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
    val sender: MemberRespDto,
    val receiver: MemberRespDto,
) {
    companion object {
        fun fromEntity(comment: Comment): CommentRespDto {
            return CommentRespDto(
                content = comment.content,
                parentId = comment.parentId,
                sender = MemberRespDto.fromEntity(comment.sender),
                receiver = MemberRespDto.fromEntity(comment.receiver),
                txId = comment.tx.id!!,
                id = comment.id,
                createdAt = comment.createdAt!!
            )
        }
    }
}
