package com.side.tiggle.domain.achievement.dto.resp

import com.side.tiggle.domain.achievement.model.AchievementConditionType
import java.time.LocalDateTime

data class AchievementRespDto(
    val id: Long,
    val code: String,
    val name: String,
    val description: String?,
    val conditionType: AchievementConditionType,
    val conditionValue: Int,
    val achieved: Boolean,
    val achievedAt: LocalDateTime?
)
