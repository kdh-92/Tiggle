package com.side.tiggle.domain.scheduler.service

import com.side.tiggle.domain.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@Service
class JobService(
    private val txRepository: TransactionRepository,
    private val statsService: StatsService
) {

    fun runNowJob() {
        // 현재 작업 로직
        val (weeklyStart, weeklyEnd) = getDate("weekly", LocalDate.now())
        val weeklyMemberTxsMap = txRepository.findByDateBetweenOrderByDateAsc(weeklyStart, weeklyEnd).groupBy { it.member.id }
        println("시작일 : ${weeklyStart}, 종료일 : ${weeklyEnd}")
        statsService.calculateAndSaveStats(weeklyMemberTxsMap, weeklyStart, weeklyEnd)
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
                val lastSunday = today.minusWeeks(1).with(DayOfWeek.SUNDAY)
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
}
