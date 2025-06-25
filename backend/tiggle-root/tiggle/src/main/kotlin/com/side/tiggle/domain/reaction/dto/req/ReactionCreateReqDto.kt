package com.side.tiggle.domain.reaction.dto.req

import com.side.tiggle.domain.reaction.model.ReactionType

data class ReactionCreateReqDto(
    val type: ReactionType
)