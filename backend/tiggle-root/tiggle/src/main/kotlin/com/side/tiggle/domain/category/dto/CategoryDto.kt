package com.side.tiggle.domain.category.dto

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member

data class CategoryDto(
    val name: String,
    val defaults: Boolean,
    val memberId: Long
) {

    fun toEntity(
        member: Member
    ): Category {
        return Category(
            member = member,
            name = name,
            defaults = defaults
        )
    }
    companion object {
        fun fromEntity(category: Category): CategoryDto {
            return CategoryDto(
                memberId = category.member.id,
                name = category.name,
                defaults = category.defaults
            )
        }
    }
}