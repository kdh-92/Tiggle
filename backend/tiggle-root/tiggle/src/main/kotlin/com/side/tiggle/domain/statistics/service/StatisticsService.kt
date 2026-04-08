package com.side.tiggle.domain.statistics.service

import com.side.tiggle.domain.statistics.dto.resp.MonthlySummaryRespDto
import com.side.tiggle.domain.statistics.dto.resp.WeeklyComparisonRespDto
import com.side.tiggle.domain.statistics.model.WeeklyStatSnapshot
import java.time.LocalDate

interface StatisticsService {

    fun getWeeklyComparison(memberId: Long, weekOffset: Int = 0): WeeklyComparisonRespDto

    fun generateWeeklySnapshot(memberId: Long, weekStartDate: LocalDate): WeeklyStatSnapshot

    fun getMonthlySummary(memberId: Long): MonthlySummaryRespDto
}
