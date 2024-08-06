package com.side.tiggle.domain.scheduler.repository

import com.side.tiggle.domain.scheduler.model.WeeklyStats
import org.springframework.data.jpa.repository.JpaRepository

interface WeeklyStatsRepository: JpaRepository<WeeklyStats, Long> {
}