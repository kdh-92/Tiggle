package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.model.Comment
import java.time.LocalDateTime

data class CommentChildRespDto(
    val id: Long,
    val txId: Long,
    val parentId: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val senderId: Long,
    val receiverId: Long,
    val childCommentCount: Int,
) {
    companion object {
        fun fromEntity(comment: Comment, childCommentCount: Int): CommentChildRespDto {
            return CommentChildRespDto(
                content = comment.content,
                parentId = comment.parentId,
                senderId = comment.senderId,
                receiverId = comment.receiverId,
                txId = comment.txId,
                id = comment.id,
                createdAt = comment.createdAt!!,
                childCommentCount = childCommentCount
            )
        }
    }
}
