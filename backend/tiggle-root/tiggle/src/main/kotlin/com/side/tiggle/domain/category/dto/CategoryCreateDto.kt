package com.side.tiggle.domain.category.dto

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member

data class CategoryCreateDto (
    val name : String
) {
    fun toEntity(member: Member): Category {
        return Category(
            name = this.name,
            defaults = false,
            member = member
        )
    }
}
