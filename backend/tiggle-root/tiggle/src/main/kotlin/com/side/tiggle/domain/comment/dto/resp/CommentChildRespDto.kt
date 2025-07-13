package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.internal.MemberInfo
import java.time.LocalDateTime

data class CommentChildRespDto(
    val id: Long,
    val txId: Long,
    val parentId: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val childCommentCount: Int,
    val sender: MemberInfo,
    val receiver: MemberInfo
) {
    companion object {
        fun fromEntity(comment: Comment, childCommentCount: Int): CommentChildRespDto {
            return CommentChildRespDto(
                content = comment.content,
                parentId = comment.parentId,
                txId = comment.txId,
                id = comment.id,
                createdAt = comment.createdAt!!,
                childCommentCount = childCommentCount,
                sender = MemberInfo.fromEntity(comment.sender),
                receiver = MemberInfo.fromEntity(comment.receiver)
            )
        }
    }
}