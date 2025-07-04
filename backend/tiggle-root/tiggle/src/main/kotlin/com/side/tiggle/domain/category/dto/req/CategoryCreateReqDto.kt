package com.side.tiggle.domain.category.dto.req

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryCreateReqDto (
    @field:NotBlank(message = "카테고리명은 필수입니다")
    @field:Size(max = 100, message = "카테고리명은 100자 이하여야 합니다")
    val name: String
) {
    fun toEntity(memberId: Long): Category {
        return Category(
            name = this.name,
            defaults = false,
            memberId = memberId
        )
    }
}
