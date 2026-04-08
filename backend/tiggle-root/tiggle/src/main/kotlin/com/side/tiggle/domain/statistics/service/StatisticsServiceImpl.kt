package com.side.tiggle.domain.statistics.service

import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.statistics.dto.resp.*
import com.side.tiggle.domain.statistics.model.WeeklyStatSnapshot
import com.side.tiggle.domain.statistics.repository.WeeklyStatSnapshotRepository
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

@Service
class StatisticsServiceImpl(
    private val weeklyStatSnapshotRepository: WeeklyStatSnapshotRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : StatisticsService {

    companion object {
        private const val ANOMALY_THRESHOLD = 1.5
        private const val HISTORY_WEEKS = 8
    }

    override fun getWeeklyComparison(memberId: Long, weekOffset: Int): WeeklyComparisonRespDto {
        val today = LocalDate.now()
        val currentWeekStart = today.minusWeeks(weekOffset.toLong())
            .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val previousWeekStart = currentWeekStart.minusWeeks(1)

        val currentSnapshot = getOrGenerateSnapshot(memberId, currentWeekStart)
        val previousSnapshot = weeklyStatSnapshotRepository
            .findByMemberIdAndWeekStartDate(memberId, previousWeekStart)
            ?: generateWeeklySnapshot(memberId, previousWeekStart)

        val currentWeekDto = toWeeklyStatRespDto(currentSnapshot)
        val previousWeekDto = toWeeklyStatRespDto(previousSnapshot)

        val changeRate = if (previousSnapshot.totalOutcome > 0) {
            ((currentSnapshot.totalOutcome - previousSnapshot.totalOutcome).toDouble() / previousSnapshot.totalOutcome) * 100
        } else {
            null
        }

        val isImproved = changeRate?.let { it < 0 }

        return WeeklyComparisonRespDto(
            currentWeek = currentWeekDto,
            previousWeek = previousWeekDto,
            changeRate = changeRate,
            isImproved = isImproved,
            isAnomaly = currentSnapshot.isAnomaly,
            anomalyRatio = currentSnapshot.anomalyRatio
        )
    }

    @Transactional
    override fun generateWeeklySnapshot(memberId: Long, weekStartDate: LocalDate): WeeklyStatSnapshot {
        val weekEndDate = weekStartDate.plusDays(6)

        // Check if snapshot already exists
        weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, weekStartDate)?.let {
            return it
        }

        // Query transaction aggregates
        val txSums = transactionRepository.sumByMemberIdAndDateRange(memberId, weekStartDate, weekEndDate)

        var totalOutcome = 0
        var totalIncome = 0
        var totalRefund = 0
        var transactionCount = 0

        for (row in txSums) {
            val txType = row[0].toString()
            val total = toBigDecimalSafe(row[1]).toInt()
            val cnt = toBigDecimalSafe(row[2]).toInt()
            transactionCount += cnt
            when (txType) {
                "OUTCOME" -> totalOutcome = total
                "INCOME" -> totalIncome = total
                "REFUND" -> totalRefund = total
            }
        }

        val avgDailyOutcome = if (totalOutcome > 0) totalOutcome / 7 else 0

        // Find top category
        val topCategoryResult = transactionRepository.findTopCategoryByOutcome(memberId, weekStartDate, weekEndDate)
        val topCategoryId = if (topCategoryResult.isNotEmpty()) {
            toBigDecimalSafe(topCategoryResult[0][0]).toLong()
        } else {
            null
        }

        // Anomaly detection: compare with average of last 8 weeks
        val recentSnapshots = weeklyStatSnapshotRepository.findTop8ByMemberIdOrderByWeekStartDateDesc(memberId)
            .filter { it.weekStartDate < weekStartDate }
            .take(HISTORY_WEEKS)

        var isAnomaly = false
        var anomalyRatio: Double? = null

        if (recentSnapshots.isNotEmpty() && totalOutcome > 0) {
            val avgOutcome = recentSnapshots.map { it.totalOutcome }.average()
            if (avgOutcome > 0) {
                val ratio = totalOutcome.toDouble() / avgOutcome
                if (ratio > ANOMALY_THRESHOLD) {
                    isAnomaly = true
                    anomalyRatio = ratio
                }
            }
        }

        val snapshot = WeeklyStatSnapshot(
            memberId = memberId,
            weekStartDate = weekStartDate,
            weekEndDate = weekEndDate,
            totalOutcome = totalOutcome,
            totalIncome = totalIncome,
            totalRefund = totalRefund,
            transactionCount = transactionCount,
            avgDailyOutcome = avgDailyOutcome,
            topCategoryId = topCategoryId,
            isAnomaly = isAnomaly,
            anomalyRatio = anomalyRatio
        )

        return weeklyStatSnapshotRepository.save(snapshot)
    }

    override fun getMonthlySummary(memberId: Long): MonthlySummaryRespDto {
        val today = LocalDate.now()
        val monthStart = today.withDayOfMonth(1)
        val monthEnd = today.with(TemporalAdjusters.lastDayOfMonth())

        // Get totals by txType
        val txSums = transactionRepository.sumByMemberIdAndDateRange(memberId, monthStart, monthEnd)

        var totalOutcome = 0
        var totalIncome = 0
        var totalRefund = 0
        var transactionCount = 0

        for (row in txSums) {
            val txType = row[0].toString()
            val total = toBigDecimalSafe(row[1]).toInt()
            val cnt = toBigDecimalSafe(row[2]).toInt()
            transactionCount += cnt
            when (txType) {
                "OUTCOME" -> totalOutcome = total
                "INCOME" -> totalIncome = total
                "REFUND" -> totalRefund = total
            }
        }

        // Category breakdown
        val categoryBreakdown = transactionRepository.findCategoryBreakdownByOutcome(memberId, monthStart, monthEnd)
        val breakdownDtos = categoryBreakdown.map { row ->
            val categoryId = toBigDecimalSafe(row[0]).toLong()
            val amount = toBigDecimalSafe(row[1]).toInt()
            val categoryName = categoryRepository.findById(categoryId)
                .map { it.name }
                .orElse("알 수 없음")
            val ratio = if (totalOutcome > 0) (amount.toDouble() / totalOutcome) * 100 else 0.0

            CategoryBreakdownDto(
                categoryId = categoryId,
                categoryName = categoryName,
                amount = amount,
                ratio = ratio
            )
        }

        return MonthlySummaryRespDto(
            year = today.year,
            month = today.monthValue,
            totalOutcome = totalOutcome,
            totalIncome = totalIncome,
            totalRefund = totalRefund,
            transactionCount = transactionCount,
            categoryBreakdown = breakdownDtos
        )
    }

    private fun getOrGenerateSnapshot(memberId: Long, weekStartDate: LocalDate): WeeklyStatSnapshot {
        return weeklyStatSnapshotRepository.findByMemberIdAndWeekStartDate(memberId, weekStartDate)
            ?: generateWeeklySnapshot(memberId, weekStartDate)
    }

    private fun toWeeklyStatRespDto(snapshot: WeeklyStatSnapshot): WeeklyStatRespDto {
        val categoryName = snapshot.topCategoryId?.let { catId ->
            categoryRepository.findById(catId).map { it.name }.orElse(null)
        }

        return WeeklyStatRespDto(
            weekStartDate = snapshot.weekStartDate,
            weekEndDate = snapshot.weekEndDate,
            totalOutcome = snapshot.totalOutcome,
            totalIncome = snapshot.totalIncome,
            totalRefund = snapshot.totalRefund,
            transactionCount = snapshot.transactionCount,
            avgDailyOutcome = snapshot.avgDailyOutcome,
            topCategoryId = snapshot.topCategoryId,
            topCategoryName = categoryName
        )
    }

    private fun toBigDecimalSafe(value: Any): BigDecimal {
        return when (value) {
            is BigDecimal -> value
            is Long -> BigDecimal.valueOf(value)
            is Int -> BigDecimal.valueOf(value.toLong())
            is Double -> BigDecimal.valueOf(value)
            else -> BigDecimal(value.toString())
        }
    }
}
