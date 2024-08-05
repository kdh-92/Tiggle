package com.side.tiggle.global.scheduler

import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Service
class JobService(
    private val txRepository: TransactionRepository
) {

    fun runNowJob() {
        // 현재 작업 로직
        val (weeklyStart, weeklyEnd) = getDate("weekly", LocalDate.now())
        val weeklyMemberTxsMap = txRepository.findByDateBetweenOrderByDateAsc(weeklyStart, weeklyEnd).groupBy { it.member.id }

        println(calculateMemberStats(weeklyMemberTxsMap))
    }

    fun generateWeeklySummary() {
        val (weeklyStart, weeklyEnd) = getDate("weekly", LocalDate.now())
        val weeklyMemberTxsMap = txRepository.findByDateBetweenOrderByDateAsc(weeklyStart, weeklyEnd).groupBy { it.member.id }
    }

    fun generateMonthlySummary() {

        println("Montly summary is running...")

        val (monthlyStart, monthlyEnd) = getDate("monthly", LocalDate.now())
        val monthlyMemberTxsMap = txRepository.findByDateBetweenOrderByDateAsc(monthlyStart, monthlyEnd).groupBy { it.member.id }
    }

    fun getDate(type: String, today: LocalDate): Pair<LocalDate, LocalDate> {
        return when (type) {
            "weekly" -> {
                val lastMonday = today.minusWeeks(1).with(DayOfWeek.MONDAY)
                val lastSunday = today.minusDays(1).with(DayOfWeek.SUNDAY)
                Pair(lastMonday, lastSunday)
            }
            "monthly" -> {
                val firstDayOfLastMonth = today.minusMonths(1).withDayOfMonth(1)
                val lastDayOfLastMonth = YearMonth.from(today.minusMonths(1)).atEndOfMonth()
                Pair(firstDayOfLastMonth, lastDayOfLastMonth)
            }
            else -> throw IllegalStateException("Unknown type $type")
        }
    }

    fun calculateMemberStats(memberTxs: Map<Long, List<Transaction>>) : Map<Long, MemberStats> {
        return memberTxs.mapValues { entry ->
            val txs = entry.value
            val totalAmount = txs.sumOf { it.amount }
            val highestAmount = txs.maxOf { it.amount }
            val lowestAmount = txs.minOf { it.amount }
            val mostFrequentCategory = txs
                .groupingBy { it.category.id }
                .eachCount()
                .maxByOrNull { it.value }?.key ?: -1

            MemberStats(
                totalAmount = totalAmount,
                highestAmount = highestAmount,
                lowestAmount = lowestAmount,
                mostFrequentCategory = mostFrequentCategory
            )
        }
    }
}

// 총 금액
// 최고 금액
// 최저 금액
// 최다 카테고리 항목
// 이전 주(달)과 차액
data class MemberStats(
    val totalAmount: Int,
    val highestAmount: Int,
    val lowestAmount: Int,
    val mostFrequentCategory: Long
)
