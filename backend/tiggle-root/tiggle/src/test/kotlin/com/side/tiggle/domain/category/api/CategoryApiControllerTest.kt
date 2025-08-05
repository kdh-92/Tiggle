package com.side.tiggle.domain.category.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.service.CategoryService
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CategoryApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @MockitoBean private val categoryService: CategoryService,
) : StringSpec() {

    override suspend fun beforeEach(testCase: TestCase) {
        reset(categoryService)
    }

    private fun createMockCategoryRespDto(
        id: Long? = 1L,
        name: String = "테스트카테고리",
        defaults: Boolean = false
    ): CategoryRespDto {
        return CategoryRespDto(
            id = id,
            name = name,
            defaults = defaults
        )
    }

    init {
        "POST /api/v1/category - 카테고리 생성 성공" {
            // given
            val memberId = -1L
            val request = CategoryCreateReqDto(
                name = "새로운카테고리"
            )

            doNothing().`when`(categoryService).createCategory(any(), any())

            // when & then
            mockMvc.perform(
                post("/api/v1/category")
                    .header("x-member-id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("카테고리가 생성되었습니다."))

            verify(categoryService).createCategory(any<CategoryCreateReqDto>(), eq(memberId))
        }

        "GET /api/v1/category - 사용자 카테고리 조회 성공" {
            // given
            val memberId = -1L
            val categories = listOf(
                createMockCategoryRespDto(1L, "식비", true),
                createMockCategoryRespDto(2L, "교통비", true),
                createMockCategoryRespDto(3L, "개인카테고리", false)
            )
            val expectedResponse = CategoryListRespDto(categories)

            given(categoryService.getCategoryByMemberIdOrDefaults(memberId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/category")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.categories").isArray)
                .andExpect(jsonPath("$.data.categories.length()").value(3))
                .andExpect(jsonPath("$.data.categories[0].name").value("식비"))
                .andExpect(jsonPath("$.data.categories[0].defaults").value(true))
                .andExpect(jsonPath("$.data.categories[1].name").value("교통비"))
                .andExpect(jsonPath("$.data.categories[1].defaults").value(true))
                .andExpect(jsonPath("$.data.categories[2].name").value("개인카테고리"))
                .andExpect(jsonPath("$.data.categories[2].defaults").value(false))

            verify(categoryService).getCategoryByMemberIdOrDefaults(memberId)
        }

        "GET /api/v1/category - 빈 카테고리 목록 조회 성공" {
            // given
            val memberId = -1L
            val expectedResponse = CategoryListRespDto(emptyList())

            given(categoryService.getCategoryByMemberIdOrDefaults(memberId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/category")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.categories").isArray)
                .andExpect(jsonPath("$.data.categories.length()").value(0))

            verify(categoryService).getCategoryByMemberIdOrDefaults(memberId)
        }

        "PUT /api/v1/category/{id} - 카테고리 수정 성공" {
            // given
            val categoryId = 1L
            val updateRequest = CategoryUpdateReqDto(
                name = "수정된카테고리명"
            )

            doNothing().`when`(categoryService).updateCategory(any(), any())

            // when & then
            mockMvc.perform(
                put("/api/v1/category/$categoryId")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest))
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("카테고리가 수정되었습니다."))

            verify(categoryService).updateCategory(eq(categoryId), any<CategoryUpdateReqDto>())
        }

        "DELETE /api/v1/category/{id} - 카테고리 삭제 성공" {
            // given
            val categoryId = 1L

            doNothing().`when`(categoryService).deleteCategory(any())

            // when & then
            mockMvc.perform(
                delete("/api/v1/category/$categoryId")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("카테고리가 삭제되었습니다."))

            verify(categoryService).deleteCategory(categoryId)
        }

        "GET /api/v1/category - 기본 카테고리와 사용자 카테고리 혼합 조회" {
            // given
            val memberId = -1L
            val mixedCategories = listOf(
                createMockCategoryRespDto(1L, "식비", true),
                createMockCategoryRespDto(2L, "교통비", true),
                createMockCategoryRespDto(3L, "문화생활", true),
                createMockCategoryRespDto(101L, "취미", false),
                createMockCategoryRespDto(102L, "투자", false)
            )
            val expectedResponse = CategoryListRespDto(mixedCategories)

            given(categoryService.getCategoryByMemberIdOrDefaults(memberId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/category")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.categories").isArray)
                .andExpect(jsonPath("$.data.categories.length()").value(5))

                .andExpect(jsonPath("$.data.categories[0].defaults").value(true))
                .andExpect(jsonPath("$.data.categories[1].defaults").value(true))
                .andExpect(jsonPath("$.data.categories[2].defaults").value(true))

                .andExpect(jsonPath("$.data.categories[3].defaults").value(false))
                .andExpect(jsonPath("$.data.categories[4].defaults").value(false))
                .andExpect(jsonPath("$.data.categories[3].name").value("취미"))
                .andExpect(jsonPath("$.data.categories[4].name").value("투자"))

            verify(categoryService).getCategoryByMemberIdOrDefaults(memberId)
        }

        "PUT /api/v1/category/{id} - 여러 카테고리 순차 수정 테스트" {
            // given
            val categoryUpdates = mapOf(
                1L to "수정된식비",
                2L to "수정된교통비",
                3L to "수정된문화생활"
            )

            doNothing().`when`(categoryService).updateCategory(any(), any())

            // when & then
            categoryUpdates.forEach { (categoryId, newName) ->
                val updateRequest = CategoryUpdateReqDto(name = newName)

                mockMvc.perform(
                    put("/api/v1/category/$categoryId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest))
                )
                    .andExpect(status().isOk)
                    .andExpect(jsonPath("$.success").value(true))
                    .andExpect(jsonPath("$.message").value("카테고리가 수정되었습니다."))

                verify(categoryService).updateCategory(eq(categoryId), any<CategoryUpdateReqDto>())
            }
        }
    }
}