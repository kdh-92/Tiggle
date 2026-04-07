package com.side.tiggle.domain.transaction.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionPageRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.support.factory.TestMemberFactory
import io.kotest.core.spec.style.StringSpec
import org.mockito.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
class TransactionApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @MockitoBean private val transactionService: TransactionService,
) : StringSpec() {
    private fun createMockTransactionRespDto(id: Long = 1L): TransactionRespDto {
        val member = TestMemberFactory.create(id = 1L)
        val category = Category("식비", false, 1L)

        return TransactionRespDto(
            id = id,
            member = MemberRespDto.fromEntity(member),
            category = CategoryRespDto.fromEntity(category),
            tagNames = listOf("커피"),
            createdAt = LocalDateTime.now(),
            parentId = null,
            imageUrls = null,
            amount = 10000,
            date = LocalDate.now(),
            content = "커피커피",
            reason = "멋사 4기 점심식사후 아인슈페너 한잔"
        )
    }

    private fun createMockTransactionPageRespDto(): TransactionPageRespDto {
        val mockDto = TransactionDtoWithCount(
            dto = createMockTransactionRespDto(1L),
            upCount = 5,
            downCount = 2,
            commentCount = 3
        )

        return TransactionPageRespDto(
            transactions = listOf(mockDto),
            pageSize = 5,
            pageNumber = 0,
            totalPages = 1,
            totalElements = 1L,
            isLast = true
        )
    }

    init {
        "GET /api/v1/transaction/{id} - 거래 상세 조회 성공" {
            // given
            val transactionId = 1L
            val expectedResponse = createMockTransactionRespDto(transactionId)

            given(transactionService.getTransactionDetail(transactionId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/transaction/$transactionId")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(transactionId))
                .andExpect(jsonPath("$.data.content").value("커피커피"))
                .andExpect(jsonPath("$.data.amount").value(10000))

            verify(transactionService).getTransactionDetail(transactionId)
        }

        "GET /api/v1/transaction - 전체 거래 페이지 조회 성공" {
            // given
            val expectedPage = createMockTransactionPageRespDto()

            given(transactionService.getCountOffsetTransaction(5, 0)).willReturn(expectedPage)

            // when & then
            mockMvc.perform(
                get("/api/v1/transaction")
                    .param("index", "0")
                    .param("pageSize", "5")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.transactions").isArray)
                .andExpect(jsonPath("$.data.pageSize").value(5))
                .andExpect(jsonPath("$.data.pageNumber").value(0))

            verify(transactionService).getCountOffsetTransaction(5, 0)
        }

        "GET /api/v1/transaction/member - 특정 유저 거래 페이지 조회 성공" {
            // given
            val memberId = 1L
            val expectedPage = createMockTransactionPageRespDto()

            given(
                transactionService.getMemberCountOffsetTransaction(
                    memberId = memberId,
                    count = 5,
                    offset = 0,
                    startDate = null,
                    endDate = null,
                    categoryIds = null,
                    tagNames = null
                )
            ).willReturn(expectedPage)

            // when & then
            mockMvc.perform(
                get("/api/v1/transaction/member")
                    .param("memberId", memberId.toString())
                    .param("index", "0")
                    .param("pageSize", "5")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.transactions").isArray)

            verify(transactionService).getMemberCountOffsetTransaction(
                memberId = memberId,
                count = 5,
                offset = 0,
                startDate = null,
                endDate = null,
                categoryIds = null,
                tagNames = null
            )
        }

        "POST /api/v1/transaction - 거래 생성 성공" {
            // given
            val request = TransactionCreateReqDto(
                categoryId = 1L,
                imageUrls = null,
                amount = 10000,
                date = LocalDate.now(),
                content = "커피",
                reason = "점심 후 커피",
                tagNames = listOf("커피", "카페")
            )

            doNothing().`when`(transactionService).createTransaction(any(), any(), any())

            val imageFile = MockMultipartFile(
                "files",
                "test.jpg",
                "image/jpeg",
                "test image data".toByteArray()
            )

            val dtoFile = MockMultipartFile(
                "dto",
                "",
                "application/json",
                objectMapper.writeValueAsString(request).toByteArray()
            )

            // when & then
            mockMvc.perform(
                multipart("/api/v1/transaction")
                    .file(imageFile)
                    .file(dtoFile)
                    .header("x-member-id", "1")
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("거래가 생성되었습니다."))

            verify(transactionService).createTransaction(eq(1L), any<TransactionCreateReqDto>(), any<List<MultipartFile>>())
        }

        "PUT /api/v1/transaction/{id} - 거래 수정 성공" {
            // given
            val transactionId = 1L
            val memberId = 1L
            val updateRequest = TransactionUpdateReqDto(
                amount = 15000,
                date = LocalDate.now(),
                content = "수정된 내용",
                reason = "수정된 이유",
                categoryId = 2L,
                tagNames = listOf("수정", "테스트")
            )

            doNothing().`when`(transactionService).updateTransaction(any(), any(), any())

            // when & then
            mockMvc.perform(
                put("/api/v1/transaction/$transactionId")
                    .header("x-member-id", memberId.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest))
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("거래가 수정되었습니다."))

            verify(transactionService).updateTransaction(eq(1L), eq(1L), any<TransactionUpdateReqDto>())
        }

        "DELETE /api/v1/transaction/{id} - 거래 삭제 성공" {
            // given
            val transactionId = 1L
            val memberId = 1L

            doNothing().`when`(transactionService).deleteTransaction(any(), any())

            // when & then
            mockMvc.perform(
                delete("/api/v1/transaction/$transactionId")
                    .header("x-member-id", memberId.toString())
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))

            verify(transactionService).deleteTransaction(eq(1L), eq(1L))
        }
    }
}