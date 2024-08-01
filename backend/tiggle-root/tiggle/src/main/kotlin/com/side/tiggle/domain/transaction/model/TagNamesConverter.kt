package com.side.tiggle.domain.transaction.model

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import javax.persistence.AttributeConverter

class TagNamesConverter: AttributeConverter<List<String>, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(p0: List<String>?): String {
        return try {
            objectMapper.writeValueAsString(p0)
        } catch (e: Exception) {
            objectMapper.writeValueAsString(emptyList<String>())
        }
    }

    override fun convertToEntityAttribute(p0: String?): List<String> {
        return try {
            objectMapper.readValue(p0, object: TypeReference<List<String>>(){})
        } catch (e: Exception) {
            emptyList<String>()
        }
    }

}