package com.side.tiggle.domain.reaction.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryRespDto
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.domain.transaction.service.TransactionService
import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class ReactionApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @MockitoBean private val reactionService: ReactionService,
    @MockitoBean private val transactionService: TransactionService,
) : StringSpec() {

    override suspend fun beforeEach(testCase: TestCase) {
        reset(reactionService, transactionService)
    }

    private fun createMockReactionRespDto(
        txId: Long = 1L,
        senderId: Long = 2L,
        receiverId: Long = 3L,
        type: ReactionType = ReactionType.UP
    ): ReactionRespDto {
        return ReactionRespDto(
            txId = txId,
            senderId = senderId,
            receiverId = receiverId,
            type = type
        )
    }

    private fun createMockTransactionInfo(id: Long = 1L, memberId: Long = 3L): TransactionInfo {
        return TransactionInfo(
            id = id,
            memberId = memberId,
            categoryId = 1L,
            imageUrls = null,
            amount = 10000,
            date = LocalDate.now(),
            content = "테스트 거래",
            reason = "테스트",
            tagNames = emptyList()
        )
    }

    init {
        "GET /api/v1/transaction/{id}/reaction - 반응 조회 성공" {
            // given
            val txId = 1L
            val senderId = 2L
            val expectedResponse = createMockReactionRespDto(txId, senderId)

            given(reactionService.getReaction(txId, senderId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/transaction/$txId/reaction")
                    .header("x-member-id", senderId.toString())
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.txId").value(txId))
                .andExpect(jsonPath("$.data.senderId").value(senderId))
                .andExpect(jsonPath("$.data.type").value("UP"))

            verify(reactionService).getReaction(txId, senderId)
        }

        "GET /api/v1/transaction/{id}/reaction - 반응이 없을 때 204 응답" {
            // given
            val txId = 1L
            val senderId = 2L

            given(reactionService.getReaction(txId, senderId)).willReturn(null)

            // when & then
            mockMvc.perform(
                get("/api/v1/transaction/$txId/reaction")
                    .header("x-member-id", senderId.toString())
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.message").value("반응이 없습니다."))

            verify(reactionService).getReaction(txId, senderId)
        }

        "GET /api/v1/transaction/{id}/reaction/summary - 반응 요약 조회 성공" {
            // given
            val txId = 1L
            val expectedSummary = ReactionSummaryRespDto(
                upCount = 5,
                downCount = 2,
                commentCount = 3
            )

            given(reactionService.getReactionSummaryDto(txId)).willReturn(expectedSummary)

            // when & then
            mockMvc.perform(
                get("/api/v1/transaction/$txId/reaction/summary")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.upCount").value(5))
                .andExpect(jsonPath("$.data.downCount").value(2))
                .andExpect(jsonPath("$.data.commentCount").value(3))
                .andExpect(jsonPath("$.message").value("반응 요약 조회 성공"))

            verify(reactionService).getReactionSummaryDto(txId)
        }

        "POST /api/v1/transaction/{id}/reaction - 반응 생성/수정 성공" {
            // given
            val txId = 1L
            val senderId = 2L
            val receiverId = 3L
            val request = ReactionCreateReqDto(type = ReactionType.UP)
            val mockTransaction = createMockTransactionInfo(txId, receiverId)

            given(transactionService.getTransactionOrThrow(txId)).willReturn(mockTransaction)
            doNothing().`when`(reactionService).upsertReaction(any(), any(), any(), any())

            // when & then
            mockMvc.perform(
                post("/api/v1/transaction/$txId/reaction")
                    .header("x-member-id", senderId.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("반응이 생성/수정되었습니다."))

            verify(transactionService).getTransactionOrThrow(txId)
            verify(reactionService).upsertReaction(eq(txId), eq(senderId), eq(receiverId), any<ReactionCreateReqDto>())
        }

        "POST /api/v1/transaction/{id}/reaction - DOWN 타입 반응 생성 성공" {
            // given
            val txId = 1L
            val senderId = 2L
            val receiverId = 3L
            val request = ReactionCreateReqDto(type = ReactionType.DOWN)
            val mockTransaction = createMockTransactionInfo(txId, receiverId)

            given(transactionService.getTransactionOrThrow(txId)).willReturn(mockTransaction)
            doNothing().`when`(reactionService).upsertReaction(any(), any(), any(), any())

            // when & then
            mockMvc.perform(
                post("/api/v1/transaction/$txId/reaction")
                    .header("x-member-id", senderId.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("반응이 생성/수정되었습니다."))

            verify(transactionService).getTransactionOrThrow(txId)
            verify(reactionService).upsertReaction(eq(txId), eq(senderId), eq(receiverId), any<ReactionCreateReqDto>())
        }

        "DELETE /api/v1/transaction/{id}/reaction - 반응 삭제 성공" {
            // given
            val txId = 1L
            val senderId = 2L

            doNothing().`when`(reactionService).deleteReaction(any(), any())

            // when & then
            mockMvc.perform(
                delete("/api/v1/transaction/$txId/reaction")
                    .header("x-member-id", senderId.toString())
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("반응이 삭제되었습니다."))

            verify(reactionService).deleteReaction(txId, senderId)
        }
    }
}