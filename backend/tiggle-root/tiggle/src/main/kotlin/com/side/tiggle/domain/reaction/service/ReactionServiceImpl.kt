package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryRespDto
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.repository.ReactionRepository
import com.side.tiggle.global.common.logging.KLog
import com.side.tiggle.global.common.logging.log
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@KLog
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

    /**
     * 사용자의 반응을 생성합니다.
     *
     * 1. 새로운 반응 생성 시도
     * 2. 이미 존재하면 무시 (동시성 처리)
     *
     * 주의: 기존 반응 타입 변경은 불가능 (예: UP→DOWN)
     * 타입 변경이 필요하면 DELETE 후 재생성 필요
     *
     * @param txId 거래 ID
     * @param senderId 반응을 보내는 사용자 ID
     * @param receiverId 반응을 받는 사용자 ID
     * @param dto 반응 생성 요청 데이터 (타입 포함)
     * @author 양병학
     * @since 2025-07-09 최초 작성
     * @modify 2025-07-09 동시성 문제 해결을 위한 예외 처리 추가
     */
    @Transactional
    override fun upsertReaction(
        txId: Long,
        senderId: Long,
        receiverId: Long,
        dto: ReactionCreateReqDto
    ) {
        try {
            val newReaction = Reaction(
                type = dto.type,
                txId = txId,
                senderId = senderId,
                receiverId = receiverId
            )
            reactionRepository.save(newReaction)
        } catch (e: DataIntegrityViolationException) {
            log.debug("이미 반응이 존재합니다: txId=$txId, senderId=$senderId")
        }
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
