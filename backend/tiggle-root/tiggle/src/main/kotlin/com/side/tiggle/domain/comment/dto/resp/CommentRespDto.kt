package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import java.time.LocalDateTime

class CommentRespDto(
    val id: Long,
    val txId: Long,
    val parentId: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val senderId: Long,
    val receiverId: Long
) {
    companion object {
        fun fromEntity(comment: Comment): CommentRespDto {
            return CommentRespDto(
                content = comment.content,
                parentId = comment.parentId,
                senderId = comment.senderId,
                receiverId = comment.receiverId,
                txId = comment.txId,
                id = comment.id,
                createdAt = comment.createdAt!!
            )
        }
    }
}
