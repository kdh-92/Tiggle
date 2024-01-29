package com.side.tiggle.domain.tag.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.side.tiggle.domain.tag.model.Tag

data class TagDto (
    val id: Long,
    val name: String
) {
    companion object {
        fun fromEntity(tag: Tag): TagDto {
            return TagDto(
                id = tag.id,
                name = tag.name
            )
        }
    }
}