package com.side.tiggle.domain.challenge.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    name = "challenge_daily_logs",
    uniqueConstraints = [UniqueConstraint(name = "uk_challenge_date", columnNames = ["challenge_id", "log_date"])],
    indexes = [Index(name = "idx_challenge_id", columnList = "challenge_id")]
)
class ChallengeDailyLog(
    @Column(name = "challenge_id", nullable = false)
    val challengeId: Long,

    @Column(name = "log_date", nullable = false)
    val logDate: LocalDate,

    @Column(name = "is_no_spend", nullable = false)
    val isNoSpend: Boolean,

    @Column(name = "outcome_amount", nullable = false)
    val outcomeAmount: Int = 0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
