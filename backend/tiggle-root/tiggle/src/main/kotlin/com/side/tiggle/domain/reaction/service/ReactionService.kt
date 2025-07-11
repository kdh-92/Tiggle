package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryRespDto
import com.side.tiggle.domain.reaction.model.ReactionType

interface ReactionService {

    fun getReaction(txId: Long, senderId: Long): ReactionRespDto?
    fun getReactionCount(txId: Long, type: ReactionType): Int
    fun upsertReaction(
        txId: Long,
        senderId: Long,
        receiverId: Long,
        dto: ReactionCreateReqDto
    )
    fun deleteReaction(txId: Long, senderId: Long)
    fun getReactionSummaryDto(txId: Long): ReactionSummaryRespDto
}
