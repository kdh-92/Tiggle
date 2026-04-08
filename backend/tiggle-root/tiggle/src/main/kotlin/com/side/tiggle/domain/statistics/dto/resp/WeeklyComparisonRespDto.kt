package com.side.tiggle.domain.statistics.dto.resp

import java.time.LocalDate

data class WeeklyComparisonRespDto(
    val currentWeek: WeeklyStatRespDto,
    val previousWeek: WeeklyStatRespDto?,
    val changeRate: Double?,
    val isImproved: Boolean?,
    val isAnomaly: Boolean,
    val anomalyRatio: Double?
)

data class WeeklyStatRespDto(
    val weekStartDate: LocalDate,
    val weekEndDate: LocalDate,
    val totalOutcome: Int,
    val totalIncome: Int,
    val totalRefund: Int,
    val transactionCount: Int,
    val avgDailyOutcome: Int,
    val topCategoryId: Long?,
    val topCategoryName: String?
)
