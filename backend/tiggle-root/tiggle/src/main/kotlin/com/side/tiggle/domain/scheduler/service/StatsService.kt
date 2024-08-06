package com.side.tiggle.domain.scheduler.service

import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.member.repository.MemberRepository
import com.side.tiggle.domain.scheduler.model.MonthlyStats
import com.side.tiggle.domain.scheduler.model.WeeklyStats
import com.side.tiggle.domain.scheduler.repository.MonthlyStatsRepository
import com.side.tiggle.domain.scheduler.repository.WeeklyStatsRepository
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Service
class StatsService(
    private val weeklyStatsRepository: WeeklyStatsRepository,
    private val monthlyStatsRepository: MonthlyStatsRepository,
    private val memberRepository: MemberRepository,
    private val categoryRepository: CategoryRepository
) {

    fun calculateAndSaveStats(memberTxs: Map<Long, List<Transaction>>, periodStart: LocalDate, periodEnd: LocalDate) {
        memberTxs.mapValues { (memberId, txs) ->
            val totalAmount = txs.sumOf { it.amount }
            val highestAmount = txs.maxOf { it.amount }
            val lowestAmount = txs.minOf { it.amount }
            val mostFrequentCategory = txs
                .groupBy { it.category.id }
                .mapValues { entry ->
                    entry.value.size to entry.value.sumOf { it.amount }
                }
                .entries
                .sortedWith(
                    compareByDescending<Map.Entry<Long?, Pair<Int, Int>>> { it.value.first }
                        .thenByDescending { it.value.second }
                )
                .firstOrNull()?.key ?: -1

            val member = memberRepository.findById(memberId).orElseThrow { NotFoundException() }
            val category = categoryRepository.findById(mostFrequentCategory).orElseThrow { NotFoundException() }

            val daysBetween = ChronoUnit.DAYS.between(periodStart, periodEnd)
            println("total : $totalAmount \n highest : $highestAmount \n lowest : $lowestAmount")
            if (daysBetween <= 7) {
                val weeklyStats = WeeklyStats(
                    member = member,
                    weeklyStart = periodStart,
                    weeklyEnd = periodEnd,
                    totalAmount = totalAmount,
                    highestAmount = highestAmount,
                    lowestAmount = lowestAmount,
                    category = category
                )
                weeklyStatsRepository.save(weeklyStats)
            } else {
                val monthlyStats = MonthlyStats(
                    member = member,
                    monthlyStart = periodStart,
                    monthlyEnd = periodEnd,
                    totalAmount = totalAmount,
                    highestAmount = highestAmount,
                    lowestAmount = lowestAmount,
                    category = category
                )
                monthlyStatsRepository.save(monthlyStats)
            }
        }
    }
}