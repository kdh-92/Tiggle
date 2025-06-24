package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
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

    fun getReaction(txId: Long, senderId: Long): ReactionRespDto? {
        val reaction = reactionRepository.findByTxIdAndSenderId(txId, senderId)
        return reaction?.let { ReactionRespDto.fromEntity(it) }
    }

    fun getReactionCount(txId: Long, type: ReactionType): Int {
        return reactionRepository.countAllByTxIdAndType(txId, type)
    }

    fun upsertReaction(txId: Long, senderId: Long, createReqDto: ReactionCreateReqDto): ReactionRespDto {
        val tx = transactionService.getTransaction(txId)
        val reaction = (reactionRepository.findByTxIdAndSenderId(txId, senderId)
            ?: Reaction(
                tx = tx,
                receiver = tx.member,
                sender = memberService.getMemberOrThrow(senderId),
                type = createReqDto.type,
            )
                ).apply {
                type = createReqDto.type
            }
        return ReactionRespDto.fromEntity(reactionRepository.save(reaction))
    }

    @Transactional
    fun deleteReaction(txId: Long, senderId: Long) {
        reactionRepository.deleteByTxIdAndSenderId(txId, senderId)
    }
}
