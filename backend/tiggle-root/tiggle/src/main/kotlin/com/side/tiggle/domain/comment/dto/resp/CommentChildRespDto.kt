package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import java.time.LocalDateTime

data class CommentChildRespDto(
    val id: Long,
    val txId: Long,
    val parentId: Long?,
    val content: String,
    val createdAt: LocalDateTime,
    val sender: MemberRespDto,
    val receiver: MemberRespDto,
    val childCommentCount: Int,
) {
    companion object {
        fun fromEntity(comment: Comment, childCommentCount: Int): CommentChildRespDto {
            return CommentChildRespDto(
                content = comment.content,
                parentId = comment.parentId,
                sender = MemberRespDto.fromEntity(comment.sender),
                receiver = MemberRespDto.fromEntity(comment.receiver),
                txId = comment.tx.id!!,
                id = comment.id,
                createdAt = comment.createdAt!!,
                childCommentCount = childCommentCount
            )
        }
    }
}
