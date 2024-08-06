package com.side.tiggle.domain.scheduler.repository

import com.side.tiggle.domain.scheduler.model.MonthlyStats
import org.springframework.data.jpa.repository.JpaRepository

interface MonthlyStatsRepository: JpaRepository<MonthlyStats, Long> {
}