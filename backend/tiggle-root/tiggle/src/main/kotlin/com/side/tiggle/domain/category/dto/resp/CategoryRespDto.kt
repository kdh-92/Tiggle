package com.side.tiggle.domain.category.dto.resp

import com.side.tiggle.domain.category.model.Category

data class CategoryRespDto(
    val id: Long?,
    val name: String,
    val defaults: Boolean
) {
    companion object {
        fun fromEntity(category: Category?): CategoryRespDto {
            return try {
                if (category != null) {
                    CategoryRespDto(
                        id = category.id!!,
                        name = category.name,
                        defaults = category.defaults
                    )
                } else {
                    CategoryRespDto(
                        id = null,
                        name = "카테고리 없음",
                        defaults = false
                    )
                }
            } catch (e: Exception) {
                CategoryRespDto(
                    id = null,
                    name = "삭제된 카테고리",
                    defaults = false
                )
            }
        }
    }
}
