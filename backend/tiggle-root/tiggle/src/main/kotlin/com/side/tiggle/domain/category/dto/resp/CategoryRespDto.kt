package com.side.tiggle.domain.category.dto.resp

import com.side.tiggle.domain.category.model.Category

data class CategoryRespDto(
    val id: Long?,
    val name: String,
    val defaults: Boolean
) {

    companion object {
        fun fromEntity(category: Category?): CategoryRespDto {
            return when {
                category == null -> CategoryRespDto(
                    id = null,
                    name = "카테고리 없음",
                    defaults = false
                )

                else -> CategoryRespDto(
                    id = category.id,
                    name = category.name,
                    defaults = category.defaults
                )
            }
        }
    }
}
