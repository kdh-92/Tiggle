package com.side.tiggle.domain.comment.dto.req

import com.side.tiggle.domain.comment.model.Comment
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CommentCreateReqDto(
    @field:NotNull(message = "트랜잭션 ID는 필수입니다")
    @field:Min(value = 1, message = "올바른 트랜잭션 ID를 입력해주세요")
    val txId: Long,

    @field:Min(value = 1, message = "올바른 부모 댓글 ID를 입력해주세요")
    val parentId: Long?,

    @field:NotBlank(message = "댓글 내용은 필수입니다")
    @field:Size(max = 255, message = "댓글 내용은 255자 이하여야 합니다")
    val content: String
) {
    fun toEntity(senderId: Long, receiverId: Long): Comment {
        return Comment(
            txId = txId,
            receiverId = receiverId,
            senderId = senderId,
            content = content
        )
    }
}