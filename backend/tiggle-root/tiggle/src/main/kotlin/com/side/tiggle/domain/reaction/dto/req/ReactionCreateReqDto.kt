package com.side.tiggle.domain.reaction.dto.req

import com.side.tiggle.domain.reaction.model.ReactionType
import jakarta.validation.constraints.NotNull

data class ReactionCreateReqDto(
    @field:NotNull(message = "리액션 타입은 필수입니다")
    val type: ReactionType
)