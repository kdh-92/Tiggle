package com.side.tiggle.domain.comment.dto.resp

import com.side.tiggle.domain.comment.dto.CommentDto
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.member.dto.MemberDto
import com.side.tiggle.domain.member.model.Member
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime
import java.util.function.Consumer
import java.util.stream.Collectors

class CommentRespDto(
    txId: Long,
    parentId: Long?,
    content: String,
    val id: Long,
    val createdAt: LocalDateTime,
    var childCount: Int = 0,
    val sender: MemberDto,
    val receiver: MemberDto
): CommentDto(
    txId, parentId, sender.id, content
) {
    fun setChildCount(service: CommentService) {
        childCount = service.getChildCount(txId, id)
    }

    companion object {
        fun fromEntity(comment: Comment): CommentRespDto {
            return CommentRespDto(
                content = comment.content,
                parentId = comment.parentId,
                sender = MemberDto.fromEntity(comment.sender),
                receiver = MemberDto.fromEntity(comment.receiver),
                txId = comment.tx.id!!,
                id = comment.id,
                createdAt = comment.createdAt!!
            )
        }

        fun fromEntityPage(comments: Page<Comment>, service: CommentService): Page<CommentRespDto> {
            val dtoList = fromEntityList(comments.content, service)
            return PageImpl(dtoList, comments.pageable, comments.totalElements)
        }

        private fun fromEntityList(comments: List<Comment>, commentService: CommentService): List<CommentRespDto> {
            return comments.map {
                CommentRespDto.fromEntity(it)
            }
        }
    }
}