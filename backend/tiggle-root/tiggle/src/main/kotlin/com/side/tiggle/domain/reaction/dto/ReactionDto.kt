package com.side.tiggle.domain.reaction.dto

import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType

open class ReactionDto(
    val txId: Long,
    val senderId: Long,
    val receiverId: Long,
    val type: ReactionType
) {

    companion object {
        fun fromEntity(reaction: Reaction): ReactionDto {
            return ReactionDto(
                txId = reaction.tx.id,
                senderId = reaction.sender.id,
                receiverId = reaction.receiver.id,
                type = reaction.type
            )
        }
    }
}