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
     * 사용자의 반응을 생성하거나 수정합니다.
     *
     * 1. 기존 반응이 있으면 타입 업데이트
     * 2. 기존 반응이 없으면 새로 생성
     * 3. 동시성 문제 대응 (중복 생성 방지)
     *
     * @param txId 거래 ID
     * @param senderId 반응을 보내는 사용자 ID
     * @param receiverId 반응을 받는 사용자 ID
     * @param dto 반응 생성/수정 요청 데이터 (타입 포함)
     * @author 양병학
     * @since 2025-07-09 최초 작성
     * @modify 2025-07-11 타입 변경 지원 추가
     */
    @Transactional
    override fun upsertReaction(
        txId: Long,
        senderId: Long,
        receiverId: Long,
        dto: ReactionCreateReqDto
    ) {
        try {
            val existingReaction = reactionRepository.findByTxIdAndSenderId(txId, senderId)

            if (existingReaction != null) {
                existingReaction.type = dto.type
                reactionRepository.save(existingReaction)
                log.debug("반응 타입 업데이트: txId=$txId, senderId=$senderId, type=${dto.type}")
            } else {
                val newReaction = Reaction(
                    type = dto.type,
                    txId = txId,
                    senderId = senderId,
                    receiverId = receiverId
                )
                reactionRepository.save(newReaction)
                log.debug("새 반응 생성: txId=$txId, senderId=$senderId, type=${dto.type}")
            }
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
