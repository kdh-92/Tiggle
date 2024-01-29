package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.reaction.dto.ReactionCreateDto
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.repository.ReactionRepository
import com.side.tiggle.domain.transaction.service.TransactionService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ReactionService(
    val reactionRepository: ReactionRepository,
    val transactionService: TransactionService,
    val memberService: MemberService
) {

    fun getReaction(txId: Long, senderId: Long): Reaction? {
        return reactionRepository.findByTxIdAndSenderId(txId, senderId)
    }

    fun getReactionCount(txId: Long, type: ReactionType): Int {
        return reactionRepository.countAllByTxIdAndType(txId, type)
    }

    fun upsertReaction(txId: Long, senderId: Long, reactionDto: ReactionCreateDto): Reaction {
        val tx = transactionService.getTransaction(txId)
        val reaction = (reactionRepository.findByTxIdAndSenderId(txId, senderId)
            ?: Reaction(
                tx = tx,
                receiver = tx.member,
                sender = memberService.getMember(senderId),
                type = reactionDto.type,
            )
        ).apply {
            type = reactionDto.type
        }
        return reactionRepository.save(reaction)
    }

    @Transactional
    fun deleteReaction(txId: Long, senderId: Long) {
        reactionRepository.deleteByTxIdAndSenderId(txId, senderId)
    }
}