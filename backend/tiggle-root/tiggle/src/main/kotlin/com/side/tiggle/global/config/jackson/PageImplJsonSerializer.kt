package com.side.tiggle.global.config.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.jackson.JsonComponent
import org.springframework.data.domain.PageImpl
import java.io.IOException

@JsonComponent
class PageImplJsonSerializer : JsonSerializer<PageImpl<*>>() {

    @Throws(IOException::class)
    override fun serialize(
        page: PageImpl<*>,
        jsonGenerator: JsonGenerator,
        serializerProvider: SerializerProvider
    ) {
        jsonGenerator.writeStartObject()

        // 페이지 컨텐츠
        jsonGenerator.writeFieldName("content")
        serializerProvider.defaultSerializeValue(page.content, jsonGenerator)

        // 페이지 정보
        jsonGenerator.writeNumberField("totalElements", page.totalElements)
        jsonGenerator.writeNumberField("totalPages", page.totalPages)
        jsonGenerator.writeNumberField("size", page.size)
        jsonGenerator.writeNumberField("number", page.number)
        jsonGenerator.writeNumberField("numberOfElements", page.numberOfElements)

        // 페이지 상태
        jsonGenerator.writeBooleanField("first", page.isFirst)
        jsonGenerator.writeBooleanField("last", page.isLast)

        // 정렬 정보
        jsonGenerator.writeFieldName("sort")
        serializerProvider.defaultSerializeValue(page.sort, jsonGenerator)

        // Pageable 정보
        jsonGenerator.writeObjectFieldStart("pageable")
        jsonGenerator.writeNumberField("offset", page.pageable.offset)
        jsonGenerator.writeNumberField("pageNumber", page.pageable.pageNumber)
        jsonGenerator.writeNumberField("pageSize", page.pageable.pageSize)
        jsonGenerator.writeBooleanField("paged", page.pageable.isPaged)
        jsonGenerator.writeBooleanField("unpaged", page.pageable.isUnpaged)
        jsonGenerator.writeEndObject()

        jsonGenerator.writeEndObject()
    }
}