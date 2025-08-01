package com.side.tiggle.domain.transaction.api

import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.support.factory.TestMemberFactory
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import org.mockito.kotlin.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate
import java.time.LocalDateTime

//@WebMvcTest(
//    controllers = [TransactionApiController::class],
//    excludeAutoConfiguration = [SecurityAutoConfiguration::class],
//    excludeFilters = [
//        ComponentScan.Filter(
//            type = FilterType.ASSIGNABLE_TYPE,
//            classes = [JwtRequestFilter::class, JwtTokenProvider::class]
//        )
//    ]
//)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TransactionApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @MockBean private val transactionService: TransactionService,
): StringSpec({

    "GET /api/v1/transaction/{id} - 거래 상세 조회 성공" {
        // given
        val member = TestMemberFactory.create(id = 1L, nickname = "홍길동")
        val memberRespDto = MemberRespDto(
            id = member.id,
            email = member.email,
            profileUrl = member.profileUrl,
            nickname = member.nickname,
            birth = member.birth
        )
        val category = CategoryRespDto(id = 1L, name = "식비", defaults = false)
        val now = LocalDateTime.now()

        val respDto = TransactionRespDto(
            id = 1L,
            member = memberRespDto,
            category = category,
            tagNames = listOf("점심", "회사"),
            createdAt = now,
            parentId = null,
            imageUrls = "https://img.url/test.png",
            amount = 12000,
            date = LocalDate.now(),
            content = "점심값",
            reason = "회식"
        )

//        every { transactionService.getTransactionDetail(1L) } returns respDto
        given(transactionService.getTransactionDetail(1L)).willReturn(respDto)

        // when & then
        mockMvc.perform(
            get("/api/v1/transaction/1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.amount").value(12000))
            .andExpect(jsonPath("$.data.member.nickname").value("홍길동"))
//            .andExpect(jsonPath("$.data.category.name").value("식비"))
    }
})

//        // given
//        val transactionId = 1L
//        val expectedResponse = createMockTransactionRespDto(transactionId)
//
//        whenever(transactionService.getTransactionDetail(transactionId)).thenReturn(expectedResponse)
//
//        // when & then
//        mockMvc.perform(
//            get("/api/v1/transaction/$transactionId")
//        )
//            .andExpect(status().isOk)
//            .andExpect(jsonPath("$.success").value(true))
//            .andExpect(jsonPath("$.data.id").value(transactionId))
//            .andExpect(jsonPath("$.data.content").value("커피커피"))
//            .andExpect(jsonPath("$.data.amount").value(10000))
//
//        verify(transactionService).getTransactionDetail(transactionId)

//
//{
//
//    override fun extensions() = listOf(SpringExtension)
//
//    @Autowired
//    lateinit var mockMvc: MockMvc
//
//    @Autowired
//    lateinit var objectMapper: ObjectMapper
//
//    @MockitoBean
//    lateinit var transactionService: TransactionService
//
//    @MockitoBean
//    lateinit var commentService: CommentService

//    @MockitoBean
//    lateinit var jwtRequestFilter: JwtRequestFilter
//
//    @MockitoBean
//    lateinit var jwtTokenProvider: JwtTokenProvider

//    private fun createMockTransactionRespDto(id: Long = 1L): TransactionRespDto {
//        val testTransaction = TestTransactionFactory.create(id = id)
//        return TransactionRespDto.fromEntity(testTransaction)
//    }
//
//    private fun createMockTransactionPageRespDto(): TransactionPageRespDto {
//        val testTransaction = TestTransactionFactory.create(id = 1L)
//
//        val mockDto = TransactionDtoWithCount(
//            dto = TransactionRespDto.fromEntity(testTransaction),
//            upCount = 5,
//            downCount = 2,
//            commentCount = 3
//        )
//
//        return TransactionPageRespDto(
//            transactions = listOf(mockDto),
//            pageSize = 5,
//            pageNumber = 0,
//            totalPages = 1,
//            totalElements = 1L,
//            isLast = true
//        )
//    }

//    init {
//        beforeEach {
//            // Mockito는 자동으로 reset되므로 수동 reset 불필요
//        }
//
//        "POST /api/v1/transaction - 거래 생성 성공" {
//            // given
//            val request = TransactionCreateReqDto(
//                categoryId = 1L,
//                imageUrls = null,
//                amount = 10000,
//                date = LocalDate.now(),
//                content = "커피",
//                reason = "점심 후 커피",
//                tagNames = listOf("커피", "카페")
//            )
//
//            doNothing().whenever(transactionService).createTransaction(any(), any(), any())
//
//            val imageFile = MockMultipartFile(
//                "files",
//                "test.jpg",
//                "image/jpeg",
//                "test image data".toByteArray()
//            )
//
//            val dtoFile = MockMultipartFile(
//                "dto",
//                "",
//                "application/json",
//                objectMapper.writeValueAsString(request).toByteArray()
//            )
//
//            // when & then
//            mockMvc.perform(
//                multipart("/api/v1/transaction")
//                    .file(imageFile)
//                    .file(dtoFile)
//                    .header("x-member-id", "1")
//            )
//                .andExpect(status().isCreated)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("거래가 생성되었습니다."))
//
//            verify(transactionService).createTransaction(eq(1L), any(), any())
//        }
//
//        "GET /api/v1/transaction/{id} - 거래 상세 조회 성공" {
//            // given
//            val transactionId = 1L
//            val expectedResponse = createMockTransactionRespDto(transactionId)
//
//            whenever(transactionService.getTransactionDetail(transactionId)).thenReturn(expectedResponse)
//
//            // when & then
//            mockMvc.perform(
//                get("/api/v1/transaction/$transactionId")
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.id").value(transactionId))
//                .andExpect(jsonPath("$.data.content").value("커피커피"))
//                .andExpect(jsonPath("$.data.amount").value(10000))
//
//            verify(transactionService).getTransactionDetail(transactionId)
//        }
//
//        "GET /api/v1/transaction - 전체 거래 페이지 조회 성공" {
//            // given
//            val expectedPage = createMockTransactionPageRespDto()
//
//            whenever(transactionService.getCountOffsetTransaction(5, 0)).thenReturn(expectedPage)
//
//            // when & then
//            mockMvc.perform(
//                get("/api/v1/transaction")
//                    .param("index", "0")
//                    .param("pageSize", "5")
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.transactions").isArray)
//                .andExpect(jsonPath("$.data.pageSize").value(5))
//                .andExpect(jsonPath("$.data.pageNumber").value(0))
//
//            verify(transactionService).getCountOffsetTransaction(5, 0)
//        }
//
//        "GET /api/v1/transaction/member - 특정 유저 거래 페이지 조회 성공" {
//            // given
//            val memberId = 1L
//            val expectedPage = createMockTransactionPageRespDto()
//
//            whenever(
//                transactionService.getMemberCountOffsetTransaction(
//                    memberId = memberId,
//                    count = 5,
//                    offset = 0,
//                    startDate = null,
//                    endDate = null,
//                    categoryIds = null,
//                    tagNames = null
//                )
//            ).thenReturn(expectedPage)
//
//            // when & then
//            mockMvc.perform(
//                get("/api/v1/transaction/member")
//                    .param("memberId", memberId.toString())
//                    .param("index", "0")
//                    .param("pageSize", "5")
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.transactions").isArray)
//
//            verify(transactionService).getMemberCountOffsetTransaction(
//                memberId = memberId,
//                count = 5,
//                offset = 0,
//                startDate = null,
//                endDate = null,
//                categoryIds = null,
//                tagNames = null
//            )
//        }
//
//        "GET /api/v1/transaction/member - 필터링 옵션과 함께 조회 성공" {
//            // given
//            val memberId = 1L
//            val startDate = LocalDate.of(2025, 1, 1)
//            val endDate = LocalDate.of(2025, 1, 31)
//            val categoryIds = listOf(1L, 2L)
//            val tagNames = listOf("커피", "점심")
//            val expectedPage = createMockTransactionPageRespDto()
//
//            whenever(
//                transactionService.getMemberCountOffsetTransaction(
//                    memberId = memberId,
//                    count = 10,
//                    offset = 0,
//                    startDate = startDate,
//                    endDate = endDate,
//                    categoryIds = categoryIds,
//                    tagNames = tagNames
//                )
//            ).thenReturn(expectedPage)
//
//            // when & then
//            mockMvc.perform(
//                get("/api/v1/transaction/member")
//                    .param("memberId", memberId.toString())
//                    .param("index", "0")
//                    .param("pageSize", "10")
//                    .param("start", "2025-01-01")
//                    .param("end", "2025-01-31")
//                    .param("category", "1", "2")
//                    .param("tagNames", "커피", "점심")
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//
//            verify(transactionService).getMemberCountOffsetTransaction(
//                memberId = memberId,
//                count = 10,
//                offset = 0,
//                startDate = startDate,
//                endDate = endDate,
//                categoryIds = categoryIds,
//                tagNames = tagNames
//            )
//        }
//
//        "PUT /api/v1/transaction/{id} - 거래 수정 성공" {
//            // given
//            val transactionId = 1L
//            val memberId = 1L
//            val updateRequest = TransactionUpdateReqDto(
//                amount = 15000,
//                date = LocalDate.now(),
//                content = "수정된 내용",
//                reason = "수정된 이유",
//                categoryId = 2L,
//                tagNames = listOf("수정", "테스트")
//            )
//
//            doNothing().whenever(transactionService).updateTransaction(any(), any(), any())
//
//            // when & then
//            mockMvc.perform(
//                put("/api/v1/transaction/$transactionId")
//                    .header("x-member-id", memberId.toString())
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(updateRequest))
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("거래가 수정되었습니다."))
//
//            verify(transactionService).updateTransaction(eq(memberId), eq(transactionId), any())
//        }
//
//        "DELETE /api/v1/transaction/{id} - 거래 삭제 성공" {
//            // given
//            val transactionId = 1L
//            val memberId = 1L
//
//            doNothing().whenever(transactionService).deleteTransaction(any(), any())
//
//            // when & then
//            mockMvc.perform(
//                delete("/api/v1/transaction/$transactionId")
//                    .header("x-member-id", memberId.toString())
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//
//            verify(transactionService).deleteTransaction(eq(memberId), eq(transactionId))
//        }
//
//        "POST /api/v1/transaction/{id}/photos - 거래 사진 추가 성공" {
//            // given
//            val transactionId = 1L
//            val memberId = 1L
//
//            doNothing().whenever(transactionService).addTransactionPhotos(any(), any(), any())
//
//            val imageFile = MockMultipartFile(
//                "files",
//                "additional.jpg",
//                "image/jpeg",
//                "additional image data".toByteArray()
//            )
//
//            // when & then
//            mockMvc.perform(
//                multipart("/api/v1/transaction/$transactionId/photos")
//                    .file(imageFile)
//                    .header("x-member-id", memberId.toString())
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("사진이 추가되었습니다."))
//
//            verify(transactionService).addTransactionPhotos(eq(memberId), eq(transactionId), any())
//        }
//
//        "DELETE /api/v1/transaction/{id}/photos/{photoIndex} - 거래 사진 삭제 성공" {
//            // given
//            val transactionId = 1L
//            val memberId = 1L
//            val photoIndex = 0
//
//            doNothing().whenever(transactionService).deleteTransactionPhoto(any(), any(), any())
//
//            // when & then
//            mockMvc.perform(
//                delete("/api/v1/transaction/$transactionId/photos/$photoIndex")
//                    .header("x-member-id", memberId.toString())
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.message").value("사진이 삭제되었습니다."))
//
//            verify(transactionService).deleteTransactionPhoto(eq(memberId), eq(transactionId), eq(photoIndex))
//        }
//
//        "GET /api/v1/transaction/{id}/comments - 거래 댓글 조회 성공" {
//            // given
//            val transactionId = 1L
//            val testComment = TestCommentFactory.create(
//                id = 1L,
//                txId = transactionId
//            )
//
//            val expectedCommentPage = CommentPageRespDto(
//                comments = listOf(CommentChildRespDto.fromEntity(testComment, 0)),
//                pageSize = 5,
//                pageNumber = 0,
//                totalPages = 1,
//                totalElements = 1L,
//                isLast = true
//            )
//
//            whenever(commentService.getParentsByTxId(transactionId, 0, 5)).thenReturn(expectedCommentPage)
//
//            // when & then
//            mockMvc.perform(
//                get("/api/v1/transaction/$transactionId/comments")
//                    .param("index", "0")
//                    .param("pageSize", "5")
//            )
//                .andExpect(status().isOk)
//                .andExpect(jsonPath("$.success").value(true))
//                .andExpect(jsonPath("$.data.comments").isArray)
//                .andExpect(jsonPath("$.data.comments[0].content").value("테스트 댓글"))
//
//            verify(commentService).getParentsByTxId(transactionId, 0, 5)
//        }
//
//        "POST /api/v1/transaction - 잘못된 요청 데이터로 400 에러" {
//            // given
//            val invalidRequest = TransactionCreateReqDto(
//                categoryId = 0L,  // 잘못된 카테고리 ID
//                imageUrls = null,
//                amount = -1000,   // 음수 금액
//                date = LocalDate.now(),
//                content = "",     // 빈 내용
//                reason = "",      // 빈 사유
//                tagNames = null
//            )
//
//            val imageFile = MockMultipartFile("files", "test.jpg", "image/jpeg", "test".toByteArray())
//            val dtoFile = MockMultipartFile(
//                "dto",
//                "",
//                "application/json",
//                objectMapper.writeValueAsString(invalidRequest).toByteArray()
//            )
//
//            // when & then
//            mockMvc.perform(
//                multipart("/api/v1/transaction")
//                    .file(imageFile)
//                    .file(dtoFile)
//                    .header("x-member-id", "1")
//            )
//                .andExpect(status().isBadRequest)
//
//            verify(transactionService, never()).createTransaction(any(), any(), any())
//        }
//
//        "GET /api/v1/transaction/{id} - 존재하지 않는 거래 조회 시 예외 처리" {
//            // given
//            val transactionId = 999L
//
//            whenever(transactionService.getTransactionDetail(transactionId))
//                .thenThrow(RuntimeException("Transaction not found"))
//
//            // when & then
//            mockMvc.perform(
//                get("/api/v1/transaction/$transactionId")
//            )
//                .andExpect(status().is5xxServerError)
//
//            verify(transactionService).getTransactionDetail(transactionId)
//        }
//
//        "PUT /api/v1/transaction/{id} - 헤더 없이 요청 시 에러" {
//            // given
//            val transactionId = 1L
//            val updateRequest = TransactionUpdateReqDto(
//                amount = 15000,
//                date = LocalDate.now(),
//                content = "수정된 내용",
//                reason = "수정된 이유",
//                categoryId = 2L,
//                tagNames = null
//            )
//
//            // when & then
//            mockMvc.perform(
//                put("/api/v1/transaction/$transactionId")
//                    // x-member-id 헤더 누락
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(objectMapper.writeValueAsString(updateRequest))
//            )
//                .andExpect(status().is4xxClientError)
//
//            verify(transactionService, never()).updateTransaction(any(), any(), any())
//        }
//    }
//}