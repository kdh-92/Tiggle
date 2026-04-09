package com.side.tiggle.domain.challenge.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "challenges",
    indexes = [Index(name = "idx_member_status", columnList = "member_id, status")]
)
class Challenge(
    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    val type: ChallengeType = ChallengeType.NO_SPEND,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: ChallengeStatus = ChallengeStatus.ACTIVE,

    @Column(name = "start_date", nullable = false)
    val startDate: LocalDate,

    @Column(name = "end_date", nullable = false)
    val endDate: LocalDate,

    @Column(name = "target_days", nullable = false)
    val targetDays: Int,

    @Column(name = "achieved_days", nullable = false)
    var achievedDays: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now()
}
