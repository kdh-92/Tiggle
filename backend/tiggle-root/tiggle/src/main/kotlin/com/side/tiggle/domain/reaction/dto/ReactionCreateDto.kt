package com.side.tiggle.domain.reaction.dto

import com.side.tiggle.domain.reaction.model.ReactionType

data class ReactionCreateDto(
    val type: ReactionType
)