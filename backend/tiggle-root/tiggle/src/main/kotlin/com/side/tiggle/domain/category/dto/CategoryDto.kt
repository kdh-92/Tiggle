package com.side.tiggle.domain.category.dto

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.model.CategoryType

data class CategoryDto(
    val name: String,
    val type: CategoryType,
    val defaults: Boolean
) {

    fun toEntity(): Category {
        return Category(
            name = name,
            type = type,
            defaults = defaults
        )
    }
    companion object {
        fun fromEntity(category: Category): CategoryDto {
            return CategoryDto(
                name = category.name,
                type = category.type,
                defaults = category.defaults
            )
        }
    }
}