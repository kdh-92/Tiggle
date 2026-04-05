package com.side.tiggle.domain.tag.dto.req

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class TagCreateReqDto(
    @field:NotBlank(message = "태그명은 필수입니다")
    @field:Size(max = 30, message = "태그명은 30자 이하여야 합니다")
    val name: String
)
