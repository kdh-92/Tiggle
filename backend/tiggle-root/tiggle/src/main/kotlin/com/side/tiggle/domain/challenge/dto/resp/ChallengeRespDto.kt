package com.side.tiggle.domain.challenge.dto.resp

import com.side.tiggle.domain.challenge.model.Challenge
import com.side.tiggle.domain.challenge.model.ChallengeStatus
import com.side.tiggle.domain.challenge.model.ChallengeType
import java.time.LocalDate
import java.time.LocalDateTime

data class ChallengeRespDto(
    val id: Long,
    val type: ChallengeType,
    val status: ChallengeStatus,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val targetDays: Int,
    val achievedDays: Int,
    val createdAt: LocalDateTime
) {
    companion object {
        fun fromEntity(challenge: Challenge): ChallengeRespDto {
            return ChallengeRespDto(
                id = challenge.id!!,
                type = challenge.type,
                status = challenge.status,
                startDate = challenge.startDate,
                endDate = challenge.endDate,
                targetDays = challenge.targetDays,
                achievedDays = challenge.achievedDays,
                createdAt = challenge.createdAt
            )
        }
    }
}
