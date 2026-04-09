package com.side.tiggle.domain.achievement.repository

import com.side.tiggle.domain.achievement.model.Achievement
import com.side.tiggle.domain.achievement.model.AchievementConditionType
import org.springframework.data.jpa.repository.JpaRepository

interface AchievementRepository : JpaRepository<Achievement, Long> {
    fun findByCode(code: String): Achievement?
    fun findByConditionType(conditionType: AchievementConditionType): List<Achievement>
}
