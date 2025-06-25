package com.side.tiggle.domain.transaction.mapper

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.stereotype.Component

@Component
class TransactionMapper(
    private val reactionService: ReactionService,
    private val commentService: CommentService
) {
    fun toDtoWithCount(tx: Transaction): TransactionDtoWithCount {
        val txId = tx.id!!
        return TransactionDtoWithCount(
            dto = TransactionRespDto.fromEntity(tx),
            upCount = reactionService.getReactionCount(txId, ReactionType.UP),
            downCount = reactionService.getReactionCount(txId, ReactionType.DOWN),
            commentCount = commentService.getParentCount(txId)
        )
    }
}
