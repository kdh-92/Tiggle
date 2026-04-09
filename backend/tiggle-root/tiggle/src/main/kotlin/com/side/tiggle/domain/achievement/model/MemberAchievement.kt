package com.side.tiggle.domain.achievement.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "member_achievements",
    uniqueConstraints = [UniqueConstraint(columnNames = ["member_id", "achievement_id"])]
)
class MemberAchievement(
    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "achievement_id", nullable = false)
    val achievementId: Long,

    @Column(name = "achieved_at", nullable = false)
    val achievedAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
