package com.side.tiggle.global.config.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

@JsonTest
class PageImplJsonSerializerTest {

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    data class TestContent(
        val id: Long,
        val name: String
    )

    @Test
    fun `페이지 직렬화 기본 테스트`() {
        val content = listOf(
            TestContent(1L, "Item 1"),
            TestContent(2L, "Item 2")
        )

        val pageable = PageRequest.of(0, 2)
        val page = PageImpl(content, pageable, 10)

        val json = objectMapper.writeValueAsString(page)

        assert(json.contains("\"content\""))
        assert(json.contains("\"totalElements\":10"))
        assert(json.contains("\"totalPages\":5"))
        assert(json.contains("\"first\":true"))
        assert(json.contains("\"last\":false"))
        assert(json.contains("\"pageable\""))
    }

    @Test
    fun `빈 페이지 직렬화 테스트`() {
        val content = emptyList<TestContent>()
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(content, pageable, 0)

        val json = objectMapper.writeValueAsString(page)

        assert(json.contains("\"content\":[]"))
        assert(json.contains("\"totalElements\":0"))
        assert(json.contains("\"first\":true"))
        assert(json.contains("\"last\":true"))
    }

    @Test
    fun `정렬된 페이지 직렬화 테스트`() {
        val content = listOf(TestContent(1L, "Apple"))
        val pageable = PageRequest.of(0, 10, Sort.by("name"))
        val page = PageImpl(content, pageable, 1)

        val json = objectMapper.writeValueAsString(page)

        assert(json.contains("\"sorted\":true"))
        assert(json.contains("\"pageable\""))
        assert(json.contains("\"pageSize\":10"))
    }
}