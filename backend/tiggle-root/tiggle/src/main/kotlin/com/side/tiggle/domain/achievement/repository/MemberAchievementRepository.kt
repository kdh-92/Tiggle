package com.side.tiggle.domain.achievement.repository

import com.side.tiggle.domain.achievement.model.MemberAchievement
import org.springframework.data.jpa.repository.JpaRepository

interface MemberAchievementRepository : JpaRepository<MemberAchievement, Long> {
    fun findAllByMemberId(memberId: Long): List<MemberAchievement>
    fun existsByMemberIdAndAchievementId(memberId: Long, achievementId: Long): Boolean
}
