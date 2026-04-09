package com.side.tiggle.domain.challenge.dto.resp

import com.side.tiggle.domain.challenge.model.ChallengeDailyLog
import java.time.LocalDate

data class DailyLogRespDto(
    val logDate: LocalDate,
    val isNoSpend: Boolean,
    val outcomeAmount: Int
) {
    companion object {
        fun fromEntity(log: ChallengeDailyLog): DailyLogRespDto {
            return DailyLogRespDto(
                logDate = log.logDate,
                isNoSpend = log.isNoSpend,
                outcomeAmount = log.outcomeAmount
            )
        }
    }
}
