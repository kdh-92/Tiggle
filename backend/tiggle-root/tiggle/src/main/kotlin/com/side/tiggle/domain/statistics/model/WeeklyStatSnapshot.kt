package com.side.tiggle.domain.statistics.model

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(
    name = "weekly_stat_snapshots",
    uniqueConstraints = [UniqueConstraint(columnNames = ["member_id", "week_start_date"])]
)
class WeeklyStatSnapshot(

    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "week_start_date", nullable = false)
    val weekStartDate: LocalDate,

    @Column(name = "week_end_date", nullable = false)
    val weekEndDate: LocalDate,

    @Column(name = "total_outcome", nullable = false)
    var totalOutcome: Int = 0,

    @Column(name = "total_income", nullable = false)
    var totalIncome: Int = 0,

    @Column(name = "total_refund", nullable = false)
    var totalRefund: Int = 0,

    @Column(name = "transaction_count", nullable = false)
    var transactionCount: Int = 0,

    @Column(name = "avg_daily_outcome", nullable = false)
    var avgDailyOutcome: Int = 0,

    @Column(name = "top_category_id")
    var topCategoryId: Long? = null,

    @Column(name = "is_anomaly", nullable = false)
    var isAnomaly: Boolean = false,

    @Column(name = "anomaly_ratio")
    var anomalyRatio: Double? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
