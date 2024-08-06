package com.side.tiggle.domain.category.dto

import com.side.tiggle.domain.category.model.Category

data class CategoryDto(
    val name: String,
    val defaults: Boolean
) {

    fun toEntity(): Category {
        return Category(
            name = name,
            defaults = defaults
        )
    }
    companion object {
        fun fromEntity(category: Category): CategoryDto {
            return CategoryDto(
                name = category.name,
                defaults = category.defaults
            )
        }
    }
}