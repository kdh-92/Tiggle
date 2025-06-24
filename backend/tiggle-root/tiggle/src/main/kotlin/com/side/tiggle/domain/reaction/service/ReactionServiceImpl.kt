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
class ReactionServiceImpl(
    val reactionRepository: ReactionRepository,
    val transactionService: TransactionService,
    val memberService: MemberService
) : ReactionService {

    override fun getReaction(txId: Long, senderId: Long): ReactionRespDto? {
        val reaction = reactionRepository.findByTxIdAndSenderId(txId, senderId)
        return reaction?.let { ReactionRespDto.fromEntity(it) }
    }

    override fun getReactionCount(txId: Long, type: ReactionType): Int {
        return reactionRepository.countAllByTxIdAndType(txId, type)
    }

    override fun upsertReaction(txId: Long, senderId: Long, createReqDto: ReactionCreateReqDto): ReactionRespDto {
        val tx = transactionService.getTransactionOrThrow(txId)
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
    override fun deleteReaction(txId: Long, senderId: Long) {
        reactionRepository.deleteByTxIdAndSenderId(txId, senderId)
    }
}