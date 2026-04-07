package com.side.tiggle.domain.tag.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.service.TagService
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
@AutoConfigureMockMvc(addFilters = false)
class TagApiControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @MockitoBean private val tagService: TagService,
) : StringSpec() {
    private fun createMockTagRespDto(id: Long = 1L, name: String = "테스트태그"): TagRespDto {
        return TagRespDto(
            id = id,
            name = name
        )
    }

    override suspend fun beforeEach(testCase: TestCase) {
        reset(tagService)
    }

    init {
        "POST /api/v1/tag - 태그 생성 성공" {
            // given
            val request = TagCreateReqDto(
                name = "새로운태그"
            )

            doNothing().`when`(tagService).createTag(any())

            // when & then
            mockMvc.perform(
                post("/api/v1/tag")
                    .header("x-member-id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
            )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("태그가 생성되었습니다."))

            verify(tagService).createTag(any<TagCreateReqDto>())
        }



        "GET /api/v1/tag/{id} - 태그 조회 성공" {
            // given
            val tagId = 1L
            val expectedResponse = createMockTagRespDto(tagId, "조회태그")

            given(tagService.getTag(tagId)).willReturn(expectedResponse)

            // when & then
            mockMvc.perform(
                get("/api/v1/tag/$tagId")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(tagId))
                .andExpect(jsonPath("$.data.name").value("조회태그"))

            verify(tagService).getTag(tagId)
        }



        "GET /api/v1/tag/all - 모든 기본 태그 조회 성공" {
            // given
            val expectedTags = listOf(
                createMockTagRespDto(1L, "기본태그1"),
                createMockTagRespDto(2L, "기본태그2"),
                createMockTagRespDto(3L, "기본태그3")
            )

            given(tagService.getAllDefaultTag()).willReturn(expectedTags)

            // when & then
            mockMvc.perform(
                get("/api/v1/tag/all")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data.length()").value(3))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("기본태그1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("기본태그2"))

            verify(tagService).getAllDefaultTag()
        }

        "GET /api/v1/tag/all - 기본 태그가 없을 때 빈 배열 반환" {
            // given
            given(tagService.getAllDefaultTag()).willReturn(emptyList())

            // when & then
            mockMvc.perform(
                get("/api/v1/tag/all")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray)
                .andExpect(jsonPath("$.data.length()").value(0))

            verify(tagService).getAllDefaultTag()
        }

        "PUT /api/v1/tag/{id} - 태그 수정 성공" {
            // given
            val tagId = 1L
            val updateRequest = TagUpdateReqDto(
                name = "수정된태그"
            )

            doNothing().`when`(tagService).updateTag(any(), any())

            // when & then
            mockMvc.perform(
                put("/api/v1/tag/$tagId")
                    .header("x-member-id", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateRequest))
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("태그가 수정되었습니다."))

            verify(tagService).updateTag(eq(tagId), any<TagUpdateReqDto>())
        }

        "DELETE /api/v1/tag/{id} - 태그 삭제 성공" {
            // given
            val tagId = 1L

            doNothing().`when`(tagService).deleteTag(any())

            // when & then
            mockMvc.perform(
                delete("/api/v1/tag/$tagId")
                    .header("x-member-id", "1")
            )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("태그가 삭제되었습니다."))

            verify(tagService).deleteTag(eq(tagId))
        }

    }
}