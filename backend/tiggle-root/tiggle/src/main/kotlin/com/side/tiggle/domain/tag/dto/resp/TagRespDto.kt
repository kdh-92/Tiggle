package com.side.tiggle.domain.tag.dto.resp

import com.side.tiggle.domain.tag.model.Tag

data class TagRespDto(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromEntity(tag: Tag): TagRespDto {
            return TagRespDto(
                id = tag.id,
                name = tag.name
            )
        }
    }
}
