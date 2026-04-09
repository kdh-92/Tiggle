package com.side.tiggle.domain.achievement.service

import com.side.tiggle.domain.achievement.dto.resp.AchievementRespDto
import com.side.tiggle.domain.achievement.model.AchievementConditionType

interface AchievementService {

    fun getAllAchievements(memberId: Long): List<AchievementRespDto>

    fun getRecentAchievements(memberId: Long, limit: Int = 5): List<AchievementRespDto>

    fun checkAndGrantAchievements(
        memberId: Long,
        conditionType: AchievementConditionType,
        currentValue: Int
    )
}
