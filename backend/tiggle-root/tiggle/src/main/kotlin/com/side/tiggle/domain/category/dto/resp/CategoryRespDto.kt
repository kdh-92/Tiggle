package com.side.tiggle.domain.category.dto.resp

import com.side.tiggle.domain.category.model.Category

data class CategoryRespDto(
    val id: Long,
    val name: String,
    val defaults: Boolean
) {
    companion object {
        fun fromEntity(category: Category): CategoryRespDto {
            return CategoryRespDto(
                id = category.id!!,
                name = category.name,
                defaults = category.defaults
            )
        }
    }
}