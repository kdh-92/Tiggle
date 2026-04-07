package com.side.tiggle.domain.comment.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.dto.resp.CommentChildRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentPageRespDto
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.member.dto.internal.MemberInfo
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
class CommentApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @MockitoBean private val commentService: CommentService,
    @MockitoBean private val transactionService: TransactionService,
) : StringSpec() {

    override suspend fun beforeEach(testCase: TestCase) {
        reset(commentService, transactionService)
    }

    private fun createMockCommentChildRespDto(
        id: Long = 1L,
        txId: Long = 1L,
        parentId: Long? = null,
        content: String = "테스트 댓글",
        childCommentCount: Int = 0
    ): CommentChildRespDto {
        return CommentChildRespDto(
            id = id,
            txId = txId,
            parentId = parentId,
            content = content,
            createdAt = LocalDateTime.now(),
            childCommentCount = childCommentCount,
            sender = MemberInfo(
                id = 2L,
                nickname = "발신자",
                profileUrl = null
            ),
            receiver = MemberInfo(
                id = 3L,
                nickname = "수신자",
                profileUrl = null
            )
        )
    }

    private fun createMockTransactionInfo(id: Long = 1L, memberId: Long = 3L): TransactionInfo {
        return TransactionInfo(
            id = id,
            memberId = memberId,
            categoryId = 1L,
            txType = com.side.tiggle.domain.transaction.model.TxType.OUTCOME,
            imageUrls = null,
            amount = 10000,
            date = LocalDate.now(),
            content = "테스트 거래",
            reason = "테스트",
            tagNames = emptyList()
        )
    }

    init {
        "GET /api/v1/comments/{id}/replies - 대댓글 조회 성공" {
            // given
            val parentId = 1L
            val childComments = listOf(
                createMockCommentChildRespDto(1L, 1L, parentId, "대댓글1"),
                createMockCommentChildRespDto(2L, 1L, parentId, "대댓글2"),
                createMockCommentChildRespDto(3L, 1L, parentId, "대댓글3")
            )
            val expectedPage = CommentPageRespDto(
                comments = childComments,
                pageNumber = 0,
                pageSize = 5,
                totalElements = 3L,
                totalPages = 1,
                isLast = true
            )

            given(commentService.getChildrenByParentId(parentId, 0, 5)).willReturn(expectedPage)

            // when & then
            mockMvc.perform(
                get("/api/v1/comments/$parentId/replies")
                    .param("index", "0")
                    .param("pageSize", "5")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.comments").isArray)
                .andExpect(jsonPath("$.data.comments.length()").value(3))
                .andExpect(jsonPath("$.data.comments[0].content").value("대댓글1"))
                .andExpect(jsonPath("$.data.comments[1].content").value("대댓글2"))
                .andExpect(jsonPath("$.data.pageNumber").value(0))
                .andExpect(jsonPath("$.data.totalElements").value(3))

            verify(commentService).getChildrenByParentId(parentId, 0, 5)
        }

        "GET /api/v1/comments/{id}/replies - 기본값으로 대댓글 조회 성공" {
            // given
            val parentId = 2L
            val expectedPage = CommentPageRespDto(
                comments = emptyList(),
                pageNumber = 0,
                pageSize = 5,
                totalElements = 0L,
                totalPages = 0,
                isLast = true
            )

            given(commentService.getChildrenByParentId(parentId, 0, 5)).willReturn(expectedPage)

            // when & then (파라미터 없이 기본값 사용)
            mockMvc.perform(
                get("/api/v1/comments/$parentId/replies")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.comments").isArray)
                .andExpect(jsonPath("$.data.comments.length()").value(0))

            verify(commentService).getChildrenByParentId(parentId, 0, 5)
        }

        "POST /api/v1/comments - 댓글 생성 성공" {
            // given
            val memberId = 1L
            val txId = 1L
            val request = CommentCreateReqDto(
                txId = txId,
                parentId = null,
                content = "새로운 댓글입니다"
            )
            val mockTransaction = createMockTransactionInfo(txId)

            given(transactionService.getTransactionOrThrow(txId)).willReturn(mockTransaction)
            doNothing().`when`(commentService).createComment(any(), any(), any())

            // when & then
            mockMvc.perform(
                post("/api/v1/comments")
                    .header("x-member-id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글이 생성되었습니다."))

            verify(transactionService).getTransactionOrThrow(txId)
            verify(commentService).createComment(eq(memberId), eq(mockTransaction), any<CommentCreateReqDto>())
        }

        "POST /api/v1/comments - 대댓글 생성 성공" {
            // given
            val memberId = 1L
            val txId = 1L
            val parentId = 5L
            val request = CommentCreateReqDto(
                txId = txId,
                parentId = parentId,
                content = "대댓글입니다"
            )
            val mockTransaction = createMockTransactionInfo(txId)

            given(transactionService.getTransactionOrThrow(txId)).willReturn(mockTransaction)
            doNothing().`when`(commentService).createComment(any(), any(), any())

            // when & then
            mockMvc.perform(
                post("/api/v1/comments")
                    .header("x-member-id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글이 생성되었습니다."))

            verify(transactionService).getTransactionOrThrow(txId)
            verify(commentService).createComment(eq(memberId), eq(mockTransaction), any<CommentCreateReqDto>())
        }

        "PUT /api/v1/comments/{id} - 댓글 수정 성공" {
            // given
            val memberId = 1L
            val commentId = 1L
            val updateRequest = CommentUpdateReqDto(
                content = "수정된 댓글 내용"
            )

            doNothing().`when`(commentService).updateComment(any(), any(), any())

            // when & then
            mockMvc.perform(
                put("/api/v1/comments/$commentId")
                    .header("x-member-id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest))
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글이 수정되었습니다."))

            verify(commentService).updateComment(eq(memberId), eq(commentId), any<CommentUpdateReqDto>())
        }

        "DELETE /api/v1/comments/{id} - 댓글 삭제 성공" {
            // given
            val memberId = 1L
            val commentId = 1L

            doNothing().`when`(commentService).deleteComment(any(), any())

            // when & then
            mockMvc.perform(
                delete("/api/v1/comments/$commentId")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("댓글이 삭제되었습니다."))

            verify(commentService).deleteComment(memberId, commentId)
        }

        "GET /api/v1/comments/{id}/comments - 거래별 부모 댓글 조회 성공" {
            // given
            val txId = 1L
            val parentComments = listOf(
                createMockCommentChildRespDto(1L, txId, null, "첫번째 댓글", 2),
                createMockCommentChildRespDto(2L, txId, null, "두번째 댓글", 0),
                createMockCommentChildRespDto(3L, txId, null, "세번째 댓글", 1)
            )
            val expectedPage = CommentPageRespDto(
                comments = parentComments,
                pageNumber = 0,
                pageSize = 5,
                totalElements = 3L,
                totalPages = 1,
                isLast = true
            )

            given(commentService.getParentsByTxId(txId, 0, 5)).willReturn(expectedPage)

            // when & then
            mockMvc.perform(
                get("/api/v1/comments/$txId/comments")
                    .param("index", "0")
                    .param("pageSize", "5")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.comments").isArray)
                .andExpect(jsonPath("$.data.comments.length()").value(3))
                .andExpect(jsonPath("$.data.comments[0].childCommentCount").value(2))
                .andExpect(jsonPath("$.data.comments[1].childCommentCount").value(0))
                .andExpect(jsonPath("$.data.comments[2].childCommentCount").value(1))

            verify(commentService).getParentsByTxId(txId, 0, 5)
        }

        "GET /api/v1/comments/{id}/comments - 기본값으로 거래별 댓글 조회 성공" {
            // given
            val txId = 2L
            val expectedPage = CommentPageRespDto(
                comments = emptyList(),
                pageNumber = 0,
                pageSize = 5,
                totalElements = 0L,
                totalPages = 0,
                isLast = true
            )

            given(commentService.getParentsByTxId(txId, 0, 5)).willReturn(expectedPage)

            // when & then
            mockMvc.perform(
                get("/api/v1/comments/$txId/comments")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.comments").isArray)
                .andExpect(jsonPath("$.data.comments.length()").value(0))

            verify(commentService).getParentsByTxId(txId, 0, 5)
        }
    }
}