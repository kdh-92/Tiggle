package com.side.tiggle.global.config.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class PageImplJsonSerializerTest : StringSpec({

    val objectMapper = ObjectMapper().apply {
        val module = SimpleModule()
        module.addSerializer(PageImpl::class.java, PageImplJsonSerializer())
        registerModule(module)
    }

    data class TestContent(
        val id: Long,
        val name: String
    )

    "페이지 직렬화 기본 테스트" {
        val content = listOf(
            TestContent(1L, "Item 1"),
            TestContent(2L, "Item 2")
        )

        val pageable = PageRequest.of(0, 2)
        val page = PageImpl(content, pageable, 10)

        val json = objectMapper.writeValueAsString(page)

        json shouldContain "\"content\""
        json shouldContain "\"totalElements\":10"
        json shouldContain "\"totalPages\":5"
        json shouldContain "\"first\":true"
        json shouldContain "\"last\":false"
        json shouldContain "\"pageable\""
    }

    "빈 페이지 직렬화 테스트" {
        val content = emptyList<TestContent>()
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(content, pageable, 0)

        val json = objectMapper.writeValueAsString(page)

        json shouldContain "\"content\":[]"
        json shouldContain "\"totalElements\":0"
        json shouldContain "\"first\":true"
        json shouldContain "\"last\":true"
    }

    "정렬된 페이지 직렬화 테스트" {
        val content = listOf(TestContent(1L, "Apple"))
        val pageable = PageRequest.of(0, 10, Sort.by("name"))
        val page = PageImpl(content, pageable, 1)

        val json = objectMapper.writeValueAsString(page)

        json shouldContain "\"pageable\""
        json shouldContain "\"pageSize\":10"
    }

    "페이지 번호와 오프셋이 정확하게 직렬화된다" {
        val content = listOf(TestContent(1L, "Item"))
        val pageable = PageRequest.of(2, 5)
        val page = PageImpl(content, pageable, 20)

        val json = objectMapper.writeValueAsString(page)

        json shouldContain "\"number\":2"
        json shouldContain "\"size\":5"
        json shouldContain "\"totalPages\":4"
        json shouldContain "\"first\":false"
        json shouldContain "\"last\":false"
        json shouldContain "\"offset\":10"
        json shouldContain "\"pageNumber\":2"
    }

    "마지막 페이지 직렬화 테스트" {
        val content = listOf(TestContent(1L, "Last"))
        val pageable = PageRequest.of(4, 2)
        val page = PageImpl(content, pageable, 9)

        val json = objectMapper.writeValueAsString(page)

        json shouldContain "\"last\":true"
        json shouldContain "\"first\":false"
        json shouldContain "\"numberOfElements\":1"
    }
})
