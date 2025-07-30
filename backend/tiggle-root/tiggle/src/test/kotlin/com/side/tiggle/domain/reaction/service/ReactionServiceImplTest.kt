package com.side.tiggle.domain.reaction.service

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.repository.ReactionRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import org.springframework.dao.DataIntegrityViolationException
import java.time.LocalDateTime

class ReactionServiceImplTest : StringSpec({

    val reactionRepository: ReactionRepository = mockk()
    val commentService: CommentService = mockk()
    val reactionService: ReactionService = ReactionServiceImpl(reactionRepository, commentService)

    beforeEach {
        clearAllMocks()
    }

    "반응을 조회합니다 - 존재하는 경우" {
        // given
        val txId = 1L
        val senderId = 2L
        val reaction = Reaction(
            type = ReactionType.UP,
            txId = txId,
            senderId = senderId,
            receiverId = 3L
        ).apply {
            id = 1L
            createdAt = LocalDateTime.now()
        }

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns reaction

        // when
        val result = reactionService.getReaction(txId, senderId)

        // then
        result shouldBe ReactionRespDto.fromEntity(reaction)
        result!!.txId shouldBe txId
        result.senderId shouldBe senderId
        result.type shouldBe ReactionType.UP
    }

    "반응을 조회합니다 - 존재하지 않는 경우" {
        // given
        val txId = 1L
        val senderId = 2L

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns null

        // when
        val result = reactionService.getReaction(txId, senderId)

        // then
        result shouldBe null
    }

    "특정 타입의 반응 개수를 조회합니다" {
        // given
        val txId = 1L
        val upCount = 5
        val downCount = 2

        every { reactionRepository.countAllByTxIdAndType(txId, ReactionType.UP) } returns upCount
        every { reactionRepository.countAllByTxIdAndType(txId, ReactionType.DOWN) } returns downCount

        // when
        val upResult = reactionService.getReactionCount(txId, ReactionType.UP)
        val downResult = reactionService.getReactionCount(txId, ReactionType.DOWN)

        // then
        upResult shouldBe upCount
        downResult shouldBe downCount
    }

    "새로운 반응을 생성합니다" {
        // given
        val txId = 1L
        val senderId = 2L
        val receiverId = 3L
        val dto = ReactionCreateReqDto(type = ReactionType.UP)
        val expected = Reaction(
            type = dto.type,
            txId = txId,
            senderId = senderId,
            receiverId = receiverId
        )

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns null
        val capturedSlot = slot<Reaction>()
        every { reactionRepository.save(capture(capturedSlot)) } returns expected

        // when
        reactionService.upsertReaction(txId, senderId, receiverId, dto)

        // then
        verify(exactly = 1) { reactionRepository.findByTxIdAndSenderId(txId, senderId) }
        verify(exactly = 1) { reactionRepository.save(any()) }

        capturedSlot.captured.txId shouldBe txId
        capturedSlot.captured.senderId shouldBe senderId
        capturedSlot.captured.receiverId shouldBe receiverId
        capturedSlot.captured.type shouldBe ReactionType.UP
    }

    "기존 반응의 타입을 수정합니다" {
        // given
        val txId = 1L
        val senderId = 2L
        val receiverId = 3L
        val dto = ReactionCreateReqDto(type = ReactionType.DOWN)

        val existingReaction = Reaction(
            type = ReactionType.UP,
            txId = txId,
            senderId = senderId,
            receiverId = receiverId
        ).apply {
            id = 1L
            createdAt = LocalDateTime.now()
        }

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns existingReaction
        every { reactionRepository.save(existingReaction) } returns existingReaction

        // when
        reactionService.upsertReaction(txId, senderId, receiverId, dto)

        // then
        existingReaction.type shouldBe ReactionType.DOWN
        verify(exactly = 1) { reactionRepository.findByTxIdAndSenderId(txId, senderId) }
        verify(exactly = 1) { reactionRepository.save(existingReaction) }
    }

    "중복 키 제약조건 위반 시 조용히 처리합니다" {
        // given
        val txId = 1L
        val senderId = 2L
        val receiverId = 3L
        val dto = ReactionCreateReqDto(type = ReactionType.UP)

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns null
        every { reactionRepository.save(any()) } throws DataIntegrityViolationException("Duplicate entry for key 'unique_tx_sender'")

        // when
        reactionService.upsertReaction(txId, senderId, receiverId, dto)

        // then
        verify(exactly = 1) { reactionRepository.findByTxIdAndSenderId(txId, senderId) }
        verify(exactly = 1) { reactionRepository.save(any()) }
        // 예외가 발생하지 않고 조용히 처리됨을 확인
    }

    "다른 데이터 무결성 오류는 재던집니다" {
        // given
        val txId = 1L
        val senderId = 2L
        val receiverId = 3L
        val dto = ReactionCreateReqDto(type = ReactionType.UP)

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns null
        every { reactionRepository.save(any()) } throws DataIntegrityViolationException("Foreign key constraint violation")

        // when & then
        io.kotest.assertions.throwables.shouldThrow<DataIntegrityViolationException> {
            reactionService.upsertReaction(txId, senderId, receiverId, dto)
        }
    }

    "반응을 삭제합니다" {
        // given
        val txId = 1L
        val senderId = 2L

        every { reactionRepository.deleteByTxIdAndSenderId(txId, senderId) } returns 1

        // when
        reactionService.deleteReaction(txId, senderId)

        // then
        verify(exactly = 1) { reactionRepository.deleteByTxIdAndSenderId(txId, senderId) }
    }

    "반응 요약 정보를 조회합니다" {
        // given
        val txId = 1L
        val upCount = 8
        val downCount = 3
        val commentCount = 12

        every { reactionRepository.countAllByTxIdAndType(txId, ReactionType.UP) } returns upCount
        every { reactionRepository.countAllByTxIdAndType(txId, ReactionType.DOWN) } returns downCount
        every { commentService.getParentCount(txId) } returns commentCount

        // when
        val result = reactionService.getReactionSummaryDto(txId)

        // then
        result.upCount shouldBe upCount
        result.downCount shouldBe downCount
        result.commentCount shouldBe commentCount

        verify(exactly = 1) { reactionRepository.countAllByTxIdAndType(txId, ReactionType.UP) }
        verify(exactly = 1) { reactionRepository.countAllByTxIdAndType(txId, ReactionType.DOWN) }
        verify(exactly = 1) { commentService.getParentCount(txId) }
    }

    "반응 요약 조회 시 모든 값이 0인 경우" {
        // given
        val txId = 999L

        every { reactionRepository.countAllByTxIdAndType(txId, ReactionType.UP) } returns 0
        every { reactionRepository.countAllByTxIdAndType(txId, ReactionType.DOWN) } returns 0
        every { commentService.getParentCount(txId) } returns 0

        // when
        val result = reactionService.getReactionSummaryDto(txId)

        // then
        result.upCount shouldBe 0
        result.downCount shouldBe 0
        result.commentCount shouldBe 0
    }

    "중복 키 예외의 다양한 메시지 패턴을 처리합니다" {
        // given
        val txId = 1L
        val senderId = 2L
        val receiverId = 3L
        val dto = ReactionCreateReqDto(type = ReactionType.UP)

        val duplicateMessages = listOf(
            "unique constraint violation",
            "Duplicate key error",
            "DUPLICATE ENTRY for key",
            "unique_tx_sender constraint violated"
        )

        duplicateMessages.forEach { message ->
            every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns null
            every { reactionRepository.save(any()) } throws DataIntegrityViolationException(message)

            // when
            reactionService.upsertReaction(txId, senderId, receiverId, dto)

            // then - 예외가 발생하지 않음을 확인
        }
    }

    "UP에서 DOWN으로 타입 변경 시나리오" {
        // given
        val txId = 1L
        val senderId = 2L
        val receiverId = 3L
        val changeDto = ReactionCreateReqDto(type = ReactionType.DOWN)

        val existingReaction = Reaction(
            type = ReactionType.UP,
            txId = txId,
            senderId = senderId,
            receiverId = receiverId
        ).apply { id = 1L }

        every { reactionRepository.findByTxIdAndSenderId(txId, senderId) } returns existingReaction
        every { reactionRepository.save(existingReaction) } returns existingReaction

        // when
        reactionService.upsertReaction(txId, senderId, receiverId, changeDto)

        // then
        existingReaction.type shouldBe ReactionType.DOWN
        verify { reactionRepository.save(existingReaction) }
    }
})