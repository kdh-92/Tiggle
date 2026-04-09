package com.side.tiggle.domain.challenge.dto.req

import com.side.tiggle.domain.challenge.model.ChallengeType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import java.time.LocalDate

data class ChallengeCreateReqDto(
    val type: ChallengeType = ChallengeType.NO_SPEND,

    @field:NotNull(message = "시작일은 필수입니다")
    val startDate: LocalDate,

    @field:NotNull(message = "종료일은 필수입니다")
    val endDate: LocalDate,

    @field:Min(value = 1, message = "목표 일수는 1일 이상이어야 합니다")
    val targetDays: Int
)
