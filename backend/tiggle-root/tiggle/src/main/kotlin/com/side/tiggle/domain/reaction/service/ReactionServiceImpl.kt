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
     * 1. 기존 반응이 있는지 조회
     * 2. 있으면 타입 수정, 없으면 새로 생성
     * 3. 동시성으로 인한 중복 생성 시 기존 데이터 업데이트 처리
     *
     * @param txId 거래 ID
     * @param senderId 반응을 보내는 사용자 ID
     * @param receiverId 반응을 받는 사용자 ID
     * @param dto 반응 생성 요청 데이터 (타입 포함)
     * @throws IllegalStateException 동시성 처리 중 기존 반응을 찾지 못한 경우 발생
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

        } catch (e: DataIntegrityViolationException) {
            log.debug("동시성으로 인한 중복 반응 생성 감지: txId=$txId, senderId=$senderId. 기존 반응을 업데이트합니다.", e)

            val existingReaction = reactionRepository.findByTxIdAndSenderId(txId, senderId)
                ?: throw IllegalStateException("Reaction 데이터를 찾을 수 없습니다. txId=$txId, senderId=$senderId")

            existingReaction.type = dto.type
            reactionRepository.save(existingReaction)

            log.debug("Reaction 업데이트 완료: txId=$txId, senderId=$senderId, newType=${dto.type}")
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
