package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.internal.MemberInfo
import java.time.LocalDateTime

data class CommentRespDto(
    val id: Long,
    val txId: Long,
    val parentId: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val sender: MemberInfo,
    val receiver: MemberInfo
) {
    companion object {
        fun fromEntity(comment: Comment): CommentRespDto {
            return CommentRespDto(
                content = comment.content,
                parentId = comment.parentId,
                txId = comment.txId,
                id = comment.id,
                createdAt = comment.createdAt!!,
                sender = MemberInfo.fromEntity(comment.sender),
                receiver = MemberInfo.fromEntity(comment.receiver)
            )
        }
    }
}