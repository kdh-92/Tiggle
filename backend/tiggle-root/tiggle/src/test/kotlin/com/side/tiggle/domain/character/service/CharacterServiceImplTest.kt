package com.side.tiggle.domain.character.service

import com.side.tiggle.domain.character.exception.CharacterException
import com.side.tiggle.domain.character.exception.error.CharacterErrorCode
import com.side.tiggle.domain.character.model.*
import com.side.tiggle.domain.character.model.Character
import com.side.tiggle.domain.character.repository.CharacterCatalogRepository
import com.side.tiggle.domain.character.repository.CharacterRepository
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.repository.MemberRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.*
import java.util.Optional

class CharacterServiceImplTest : StringSpec({

    val characterRepository: CharacterRepository = mockk()
    val characterCatalogRepository: CharacterCatalogRepository = mockk()
    val memberRepository: MemberRepository = mockk()
    val characterService = CharacterServiceImpl(
        characterRepository,
        characterCatalogRepository,
        memberRepository
    )

    afterTest {
        clearMocks(characterRepository, characterCatalogRepository, memberRepository)
    }

    "기존 캐릭터가 있으면 그대로 반환한다" {
        val memberId = 1L
        val existing = Character(memberId = memberId)
        every { characterRepository.findByMemberId(memberId) } returns existing

        val result = characterService.getOrCreateCharacter(memberId)

        result shouldBe existing
        verify(exactly = 0) { memberRepository.findById(any()) }
    }

    "기존 캐릭터가 없으면 새로 생성하고 색상을 부여한다" {
        val memberId = 1L
        val member = Member(
            email = "test@example.com",
            profileUrl = null,
            nickname = "test"
        ).apply { id = memberId }

        every { characterRepository.findByMemberId(memberId) } returns null
        every { memberRepository.findById(memberId) } returns Optional.of(member)
        every { memberRepository.save(any()) } returns member

        val capturedCharacter = slot<Character>()
        every { characterRepository.save(capture(capturedCharacter)) } answers { capturedCharacter.captured }

        val result = characterService.getOrCreateCharacter(memberId)

        result.memberId shouldBe memberId
        result.stage shouldBe CharacterStage.EGG
        member.colorHue shouldNotBe null
        member.colorSaturation shouldNotBe null
        member.colorLightness shouldNotBe null
        member.colorRarity shouldNotBe null
        verify(exactly = 1) { memberRepository.save(member) }
    }

    "회원이 없으면 예외를 던진다" {
        val memberId = 999L
        every { characterRepository.findByMemberId(memberId) } returns null
        every { memberRepository.findById(memberId) } returns Optional.empty()

        val exception = shouldThrow<CharacterException> {
            characterService.getOrCreateCharacter(memberId)
        }
        exception.getErrorCode() shouldBe CharacterErrorCode.MEMBER_NOT_FOUND
    }

    "이미 색상이 있는 회원은 색상을 다시 생성하지 않는다" {
        val memberId = 1L
        val member = Member(
            email = "test@example.com",
            profileUrl = null,
            nickname = "test"
        ).apply {
            id = memberId
            colorHue = 120
            colorSaturation = 70
            colorLightness = 55
            colorRarity = ColorRarity.NORMAL
        }

        every { characterRepository.findByMemberId(memberId) } returns null
        every { memberRepository.findById(memberId) } returns Optional.of(member)
        every { characterRepository.save(any()) } answers { firstArg() }

        characterService.getOrCreateCharacter(memberId)

        // Should not call memberRepository.save since color already exists
        verify(exactly = 0) { memberRepository.save(any()) }
        member.colorHue shouldBe 120
    }

    "incrementEggRecords가 eggRecords를 증가시키고 phase를 업데이트한다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            eggRecords = 9
            eggPhase = 0
        }

        every { characterRepository.findByMemberId(memberId) } returns character
        every { characterRepository.save(any()) } answers { firstArg() }

        characterService.incrementEggRecords(memberId)

        character.eggRecords shouldBe 10
        character.eggPhase shouldBe 1  // phase 1 threshold is 10
        verify(exactly = 1) { characterRepository.save(character) }
    }

    "incrementEggRecords는 ACTIVE 캐릭터에 대해 아무것도 하지 않는다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            stage = CharacterStage.ACTIVE
        }

        every { characterRepository.findByMemberId(memberId) } returns character

        characterService.incrementEggRecords(memberId)

        verify(exactly = 0) { characterRepository.save(any()) }
    }

    "eggRecords가 20이면 phase 2가 된다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            eggRecords = 19
            eggPhase = 1
        }

        every { characterRepository.findByMemberId(memberId) } returns character
        every { characterRepository.save(any()) } answers { firstArg() }

        characterService.incrementEggRecords(memberId)

        character.eggRecords shouldBe 20
        character.eggPhase shouldBe 2
    }

    "eggRecords가 25이면 phase 3가 된다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            eggRecords = 24
            eggPhase = 2
        }

        every { characterRepository.findByMemberId(memberId) } returns character
        every { characterRepository.save(any()) } answers { firstArg() }

        characterService.incrementEggRecords(memberId)

        character.eggRecords shouldBe 25
        character.eggPhase shouldBe 3
    }

    "checkAndHatch: eggRecords >= 30이면 부화한다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            eggRecords = 30
            eggPhase = 3
        }

        every { characterRepository.findByMemberId(memberId) } returns character
        every { characterRepository.save(any()) } answers { firstArg() }

        val result = characterService.checkAndHatch(memberId)

        result shouldBe true
        character.stage shouldBe CharacterStage.ACTIVE
        character.tier shouldBe CharacterTier.COMMON
        character.characterPath shouldBe CharacterPath.GOLD
        character.level shouldBe 1
        character.hatchedAt shouldNotBe null
    }

    "checkAndHatch: eggRecords < 30이면 부화하지 않는다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            eggRecords = 29
            eggPhase = 3
        }

        every { characterRepository.findByMemberId(memberId) } returns character

        val result = characterService.checkAndHatch(memberId)

        result shouldBe false
        character.stage shouldBe CharacterStage.EGG
    }

    "checkAndHatch: 이미 ACTIVE인 캐릭터는 false를 반환한다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            stage = CharacterStage.ACTIVE
            eggRecords = 30
        }

        every { characterRepository.findByMemberId(memberId) } returns character

        val result = characterService.checkAndHatch(memberId)

        result shouldBe false
    }

    "addExperience: 경험치를 추가하고 레벨업한다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            stage = CharacterStage.ACTIVE
            characterPath = CharacterPath.GOLD
            tier = CharacterTier.COMMON
            level = 1
            experience = 0
        }

        val level2Catalog = CharacterCatalog(
            tier = CharacterTier.COMMON,
            path = CharacterPath.GOLD,
            level = 2,
            name = "뭉치",
            nameEn = "clump",
            description = "모이기 시작",
            requiredExp = 100,
            imageKey = "gold_2_clump"
        )

        every { characterRepository.findByMemberId(memberId) } returns character
        every { characterCatalogRepository.findByPathAndLevel(CharacterPath.GOLD, 2) } returns level2Catalog
        every { characterCatalogRepository.findByPathAndLevel(CharacterPath.GOLD, 3) } returns null
        every { characterRepository.save(any()) } answers { firstArg() }

        characterService.addExperience(memberId, 150, "test")

        character.experience shouldBe 150
        character.level shouldBe 2
    }

    "addExperience: EGG 상태에서는 경험치를 추가하지 않는다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            stage = CharacterStage.EGG
        }

        every { characterRepository.findByMemberId(memberId) } returns character

        characterService.addExperience(memberId, 100, "test")

        character.experience shouldBe 0
        verify(exactly = 0) { characterRepository.save(any()) }
    }

    "addExperience: 경험치가 부족하면 레벨업하지 않는다" {
        val memberId = 1L
        val character = Character(memberId = memberId).apply {
            stage = CharacterStage.ACTIVE
            characterPath = CharacterPath.GOLD
            tier = CharacterTier.COMMON
            level = 1
            experience = 0
        }

        val level2Catalog = CharacterCatalog(
            tier = CharacterTier.COMMON,
            path = CharacterPath.GOLD,
            level = 2,
            name = "뭉치",
            nameEn = "clump",
            description = "모이기 시작",
            requiredExp = 100,
            imageKey = "gold_2_clump"
        )

        every { characterRepository.findByMemberId(memberId) } returns character
        every { characterCatalogRepository.findByPathAndLevel(CharacterPath.GOLD, 2) } returns level2Catalog
        every { characterRepository.save(any()) } answers { firstArg() }

        characterService.addExperience(memberId, 50, "test")

        character.experience shouldBe 50
        character.level shouldBe 1
    }

    "캐릭터가 없으면 getCharacterDetail이 예외를 던진다" {
        val memberId = 999L
        every { characterRepository.findByMemberId(memberId) } returns null

        val exception = shouldThrow<CharacterException> {
            characterService.getCharacterDetail(memberId)
        }
        exception.getErrorCode() shouldBe CharacterErrorCode.CHARACTER_NOT_FOUND
    }
})
