package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.repository.ReactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReactionService(
    val reactionRepository: ReactionRepository
) {

    fun getReaction(txId: Long, senderId: Long): ReactionRespDto? {
        val reaction = reactionRepository.findByTxIdAndSenderId(txId, senderId)
        return reaction?.let { ReactionRespDto.fromEntity(it) }
    }

    fun getReactionCount(txId: Long, type: ReactionType): Int {
        return reactionRepository.countAllByTxIdAndType(txId, type)
    }

    fun upsertReaction(txId: Long, senderId: Long, receiverId: Long, dto: ReactionCreateReqDto): ReactionRespDto {
        val reaction = (reactionRepository.findByTxIdAndSenderId(txId, senderId)
            ?: Reaction(
                txId = txId,
                receiverId = receiverId,
                senderId = senderId,
                type = dto.type,
            )
                ).apply {
                type = dto.type
            }
        return ReactionRespDto.fromEntity(reactionRepository.save(reaction))
    }

    @Transactional
    fun deleteReaction(txId: Long, senderId: Long) {
        reactionRepository.deleteByTxIdAndSenderId(txId, senderId)
    }
}
