package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.MemberDto
import java.time.LocalDateTime

class CommentChildRespDto(
    val id: Long,
    val txId: Long,
    val parentId: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val sender: MemberDto,
    val receiver: MemberDto,
    val childCommentCount: Int,
) {
    companion object {
        fun fromEntity(comment: Comment, childCommentCount: Int): CommentChildRespDto {
            return CommentChildRespDto(
                content = comment.content,
                parentId = comment.parentId,
                sender = MemberDto.fromEntity(comment.sender),
                receiver = MemberDto.fromEntity(comment.receiver),
                txId = comment.tx.id!!,
                id = comment.id,
                createdAt = comment.createdAt!!,
                childCommentCount = childCommentCount
            )
        }
    }
}
