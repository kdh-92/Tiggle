package com.side.tiggle.domain.statistics.repository

import com.side.tiggle.domain.statistics.model.WeeklyStatSnapshot
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WeeklyStatSnapshotRepository : JpaRepository<WeeklyStatSnapshot, Long> {

    fun findByMemberIdAndWeekStartDate(memberId: Long, weekStartDate: LocalDate): WeeklyStatSnapshot?

    fun findByMemberIdOrderByWeekStartDateDesc(memberId: Long, pageable: Pageable): Page<WeeklyStatSnapshot>

    fun findTop8ByMemberIdOrderByWeekStartDateDesc(memberId: Long): List<WeeklyStatSnapshot>
}
