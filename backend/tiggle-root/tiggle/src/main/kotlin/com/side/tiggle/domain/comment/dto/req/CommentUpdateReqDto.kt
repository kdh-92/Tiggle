package com.side.tiggle.domain.comment.dto.req

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CommentUpdateReqDto(
    @field:NotBlank(message = "댓글 내용은 필수입니다")
    @field:Size(max = 255, message = "댓글 내용은 255자 이하여야 합니다")
    val content: String
)
