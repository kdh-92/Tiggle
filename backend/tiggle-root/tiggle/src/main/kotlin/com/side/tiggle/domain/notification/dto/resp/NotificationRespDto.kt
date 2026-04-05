package com.side.tiggle.domain.notification.dto.resp

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.notification.model.Notification
import java.time.LocalDateTime

data class NotificationRespDto(
    val id: Long,
    val title: String?,
    val content: String?,
    val createdAt: LocalDateTime,
    val viewedAt: LocalDateTime?
) {
    var sender: MemberRespDto? = null
    var receiver: MemberRespDto? = null
    var comment: CommentRespDto? = null

    companion object {
        fun fromEntity(notification: Notification): NotificationRespDto {
            return NotificationRespDto(
                id = notification.id,
                title = notification.title,
                content = notification.content,
                createdAt = notification.createdAt,
                viewedAt = notification.viewedAt
            )
        }
    }
}