package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryRespDto
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.repository.ReactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReactionServiceImpl(
    private val reactionRepository: ReactionRepository,
    private val commentService: CommentService,
) : ReactionService {

    override fun getReaction(txId: Long, senderId: Long): ReactionRespDto? {
        val reaction = reactionRepository.findByTxIdAndSenderId(txId, senderId)
        return reaction?.let { ReactionRespDto.fromEntity(it) }
    }

    override fun getReactionCount(txId: Long, type: ReactionType): Int {
        return reactionRepository.countAllByTxIdAndType(txId, type)
    }

    override fun upsertReaction(
        txId: Long,
        senderId: Long,
        receiverId: Long,
        dto: ReactionCreateReqDto
    ) {
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

        reactionRepository.save(reaction)
    }

    @Transactional
    override fun deleteReaction(txId: Long, senderId: Long) {
        reactionRepository.deleteByTxIdAndSenderId(txId, senderId)
    }

    override fun getReactionSummaryDto(txId: Long): ReactionSummaryRespDto {
        val up = getReactionCount(txId, ReactionType.UP)
        val down = getReactionCount(txId, ReactionType.DOWN)
        val comments = commentService.getParentCount(txId)
        return ReactionSummaryRespDto(up, down, comments)
    }
}
