package com.side.tiggle.domain.achievement.service

import com.side.tiggle.domain.achievement.dto.resp.AchievementRespDto
import com.side.tiggle.domain.achievement.model.AchievementConditionType
import com.side.tiggle.domain.achievement.model.MemberAchievement
import com.side.tiggle.domain.achievement.repository.AchievementRepository
import com.side.tiggle.domain.achievement.repository.MemberAchievementRepository
import com.side.tiggle.domain.character.service.CharacterService
import com.side.tiggle.domain.item.service.ItemService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AchievementServiceImpl(
    private val achievementRepository: AchievementRepository,
    private val memberAchievementRepository: MemberAchievementRepository,
    private val itemService: ItemService,
    private val characterService: CharacterService
) : AchievementService {

    override fun getAllAchievements(memberId: Long): List<AchievementRespDto> {
        val allAchievements = achievementRepository.findAll()
        val memberAchievements = memberAchievementRepository.findAllByMemberId(memberId)
        val achievedMap = memberAchievements.associateBy { it.achievementId }

        return allAchievements.map { achievement ->
            val memberAchievement = achievedMap[achievement.id]
            AchievementRespDto(
                id = achievement.id!!,
                code = achievement.code,
                name = achievement.name,
                description = achievement.description,
                conditionType = achievement.conditionType,
                conditionValue = achievement.conditionValue,
                achieved = memberAchievement != null,
                achievedAt = memberAchievement?.achievedAt
            )
        }
    }

    override fun getRecentAchievements(memberId: Long, limit: Int): List<AchievementRespDto> {
        val memberAchievements = memberAchievementRepository.findAllByMemberId(memberId)
            .sortedByDescending { it.achievedAt }
            .take(limit)

        val achievementIds = memberAchievements.map { it.achievementId }
        val achievementMap = achievementRepository.findAllById(achievementIds).associateBy { it.id }

        return memberAchievements.mapNotNull { ma ->
            val achievement = achievementMap[ma.achievementId] ?: return@mapNotNull null
            AchievementRespDto(
                id = achievement.id!!,
                code = achievement.code,
                name = achievement.name,
                description = achievement.description,
                conditionType = achievement.conditionType,
                conditionValue = achievement.conditionValue,
                achieved = true,
                achievedAt = ma.achievedAt
            )
        }
    }

    @Transactional
    override fun checkAndGrantAchievements(
        memberId: Long,
        conditionType: AchievementConditionType,
        currentValue: Int
    ) {
        val candidates = achievementRepository.findByConditionType(conditionType)

        for (achievement in candidates) {
            // Skip if already achieved
            if (memberAchievementRepository.existsByMemberIdAndAchievementId(memberId, achievement.id!!)) {
                continue
            }

            // Check if condition is met
            if (currentValue >= achievement.conditionValue) {
                // Grant achievement
                val memberAchievement = MemberAchievement(
                    memberId = memberId,
                    achievementId = achievement.id!!
                )
                memberAchievementRepository.save(memberAchievement)

                // Grant reward item if any
                if (achievement.rewardItemId != null) {
                    itemService.grantItem(memberId, achievement.rewardItemId!!, "업적 달성: ${achievement.name}")
                }

                // Grant reward experience if any
                if (achievement.rewardExp > 0) {
                    characterService.addExperience(memberId, achievement.rewardExp, "업적 달성: ${achievement.name}")
                }
            }
        }
    }
}
