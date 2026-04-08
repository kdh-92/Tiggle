package com.side.tiggle.domain.character.service

import com.side.tiggle.domain.character.dto.resp.*
import com.side.tiggle.domain.character.exception.CharacterException
import com.side.tiggle.domain.character.exception.error.CharacterErrorCode
import com.side.tiggle.domain.character.model.Character
import com.side.tiggle.domain.character.model.CharacterPath
import com.side.tiggle.domain.character.model.CharacterStage
import com.side.tiggle.domain.character.model.CharacterTier
import com.side.tiggle.domain.character.repository.CharacterCatalogRepository
import com.side.tiggle.domain.character.repository.CharacterRepository
import com.side.tiggle.domain.character.util.ColorGenerator
import com.side.tiggle.domain.member.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CharacterServiceImpl(
    private val characterRepository: CharacterRepository,
    private val characterCatalogRepository: CharacterCatalogRepository,
    private val memberRepository: MemberRepository
) : CharacterService {

    companion object {
        private const val EGG_HATCH_THRESHOLD = 30
        private val EGG_PHASE_THRESHOLDS = intArrayOf(0, 10, 20, 25)
    }

    @Transactional
    override fun getOrCreateCharacter(memberId: Long): Character {
        characterRepository.findByMemberId(memberId)?.let { return it }

        val member = memberRepository.findById(memberId)
            .orElseThrow { CharacterException(CharacterErrorCode.MEMBER_NOT_FOUND) }

        // Generate and persist color if not yet set
        if (member.colorHue == null) {
            val color = ColorGenerator.generate(memberId, member.email)
            member.colorHue = color.hue
            member.colorSaturation = color.saturation
            member.colorLightness = color.lightness
            member.colorRarity = color.rarity
            memberRepository.save(member)
        }

        val character = Character(memberId = memberId)
        return characterRepository.save(character)
    }

    override fun getCharacterDetail(memberId: Long): CharacterDetailRespDto {
        val character = characterRepository.findByMemberId(memberId)
            ?: throw CharacterException(CharacterErrorCode.CHARACTER_NOT_FOUND)

        val member = memberRepository.findById(memberId)
            .orElseThrow { CharacterException(CharacterErrorCode.MEMBER_NOT_FOUND) }

        // Build color DTO
        val colorDto = ColorRespDto(
            hue = member.colorHue ?: 0,
            saturation = member.colorSaturation ?: 50,
            lightness = member.colorLightness ?: 50,
            rarity = member.colorRarity ?: com.side.tiggle.domain.character.model.ColorRarity.NORMAL,
            cssValue = "hsl(${member.colorHue ?: 0}, ${member.colorSaturation ?: 50}%, ${member.colorLightness ?: 50}%)"
        )

        // Build current form DTO if character has hatched
        var currentForm: CharacterFormDto? = null
        var nextLevelExp: Int? = null

        if (character.stage == CharacterStage.ACTIVE && character.characterPath != null) {
            val catalog = characterCatalogRepository.findByPathAndLevel(
                character.characterPath!!, character.level
            )
            if (catalog != null) {
                currentForm = CharacterFormDto(
                    name = catalog.name,
                    nameEn = catalog.nameEn,
                    description = catalog.description,
                    imageKey = catalog.imageKey
                )
            }

            // Find next level required exp
            val nextCatalog = characterCatalogRepository.findByPathAndLevel(
                character.characterPath!!, character.level + 1
            )
            nextLevelExp = nextCatalog?.requiredExp
        }

        // Build egg status if still in egg stage
        val eggStatus = if (character.stage == CharacterStage.EGG) {
            val nextPhaseAt = if (character.eggPhase < EGG_PHASE_THRESHOLDS.size - 1) {
                EGG_PHASE_THRESHOLDS[character.eggPhase + 1]
            } else {
                EGG_HATCH_THRESHOLD
            }
            EggStatusDto(
                phase = character.eggPhase,
                records = character.eggRecords,
                nextPhaseAt = nextPhaseAt
            )
        } else {
            null
        }

        return CharacterDetailRespDto(
            stage = character.stage,
            tier = character.tier,
            path = character.characterPath,
            level = character.level,
            experience = character.experience,
            nextLevelExp = nextLevelExp,
            currentForm = currentForm,
            color = colorDto,
            egg = eggStatus
        )
    }

    @Transactional
    override fun addExperience(memberId: Long, amount: Int, reason: String) {
        val character = characterRepository.findByMemberId(memberId)
            ?: throw CharacterException(CharacterErrorCode.CHARACTER_NOT_FOUND)

        if (character.stage != CharacterStage.ACTIVE) return

        character.experience += amount
        character.updatedAt = LocalDateTime.now()

        // Check for level up
        if (character.characterPath != null) {
            var leveled = true
            while (leveled) {
                val nextCatalog = characterCatalogRepository.findByPathAndLevel(
                    character.characterPath!!, character.level + 1
                )
                if (nextCatalog != null && character.experience >= nextCatalog.requiredExp) {
                    character.level = nextCatalog.level
                    leveled = true
                } else {
                    leveled = false
                }
            }
        }

        characterRepository.save(character)
    }

    @Transactional
    override fun incrementEggRecords(memberId: Long) {
        val character = characterRepository.findByMemberId(memberId)
            ?: throw CharacterException(CharacterErrorCode.CHARACTER_NOT_FOUND)

        if (character.stage != CharacterStage.EGG) return

        character.eggRecords += 1
        character.updatedAt = LocalDateTime.now()

        // Update egg phase based on thresholds
        for (i in EGG_PHASE_THRESHOLDS.indices.reversed()) {
            if (character.eggRecords >= EGG_PHASE_THRESHOLDS[i]) {
                character.eggPhase = i
                break
            }
        }

        characterRepository.save(character)
    }

    @Transactional
    override fun checkAndHatch(memberId: Long): Boolean {
        val character = characterRepository.findByMemberId(memberId)
            ?: throw CharacterException(CharacterErrorCode.CHARACTER_NOT_FOUND)

        if (character.stage != CharacterStage.EGG) return false
        if (character.eggRecords < EGG_HATCH_THRESHOLD) return false

        val (tier, path) = determineCharacterPath(memberId)

        character.stage = CharacterStage.ACTIVE
        character.tier = tier
        character.characterPath = path
        character.level = 1
        character.hatchedAt = LocalDateTime.now()
        character.updatedAt = LocalDateTime.now()

        characterRepository.save(character)
        return true
    }

    /**
     * 거래 통계를 기반으로 캐릭터 경로를 결정합니다.
     * 초기 구현에서는 기본값 COMMON/GOLD를 반환합니다.
     */
    internal fun determineCharacterPath(memberId: Long): Pair<CharacterTier, CharacterPath> {
        return Pair(CharacterTier.COMMON, CharacterPath.GOLD)
    }
}
