package com.side.tiggle.domain.category.dto.req

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member

data class CategoryCreateReqDto (
    val name : String
) {
    fun toEntity(memberId: Long): Category {
        return Category(
            name = this.name,
            defaults = false,
            memberId = memberId
        )
    }
}
