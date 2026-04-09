package com.side.tiggle.domain.challenge.repository

import com.side.tiggle.domain.challenge.model.ChallengeDailyLog
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ChallengeDailyLogRepository : JpaRepository<ChallengeDailyLog, Long> {

    fun findByChallengeIdOrderByLogDateDesc(challengeId: Long): List<ChallengeDailyLog>

    fun findByChallengeIdAndLogDate(challengeId: Long, logDate: LocalDate): ChallengeDailyLog?
}
