package com.side.tiggle.domain.notification.dto

import com.side.tiggle.domain.comment.dto.CommentDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
import com.side.tiggle.domain.notification.model.Notification
import com.side.tiggle.domain.transaction.dto.TransactionDto
import java.time.LocalDateTime

data class NotificationDto(
    val id: Long,
    val title: String?,
    val content: String?,
    val createdAt: LocalDateTime,
    val viewedAt: LocalDateTime?
) {
    var sender: MemberResponseDto? = null
    var receiver: MemberResponseDto? = null
    var comment: CommentRespDto? = null

    companion object {
        fun fromEntity(
            noti: Notification,
        ): NotificationDto {
            return NotificationDto(
                id = noti.id,
                title = noti.title,
                content = noti.content,
                createdAt = noti.createdAt,
                viewedAt = noti.viewedAt
            )
        }
    }
}