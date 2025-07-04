package com.side.tiggle.domain.notification.service

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.notification.NotificationProducer
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.notification.exception.NotificationException
import com.side.tiggle.domain.notification.exception.error.NotificationErrorCode
import com.side.tiggle.domain.notification.repository.NotificationRepository
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
    private val notificationProducer: NotificationProducer,
) : NotificationService {

    override fun getAllByMemberId(memberId: Long): List<NotificationRespDto> {
        val notiList = notificationRepository.findAllByReceiverId(memberId)
        return notiList.map { notification ->
            val sender = notification.sender?.let { MemberRespDto.fromEntity(it) }

            val receiver = notification.receiver?.let { MemberRespDto.fromEntity(it) }
                ?: throw IllegalStateException("Notification receiver cannot be null")

            val comment = notification.comment?.let { CommentRespDto.fromEntity(it) }
                ?: throw IllegalStateException("Notification comment cannot be null")

            NotificationRespDto.fromEntity(notification).apply {
                this.sender = sender
                this.receiver = receiver
                this.comment = comment
            }
        }
    }

    override fun sendCommentNotification(
        comment: Comment,
        parentComment: Comment?,
        tx: TransactionInfo,
        senderId: Long
    ) {
        val receiverId = parentComment?.senderId ?: tx.memberId
        val type = if (parentComment != null) NotificationProduceDto.Type.REPLY else NotificationProduceDto.Type.COMMENT

        notificationProducer.send(
            NotificationProduceDto(
                imageUrl = null,
                content = comment.content,
                receiverId = receiverId,
                senderId = senderId,
                title = tx.content,
                txId = tx.id,
                commentId = comment.id,
                type = type
            )
        )
    }

    override fun readNotificationById(memberId: Long, id: Long) {
        val noti = notificationRepository.findById(id).orElseThrow {
            NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND)
        }
        if (noti.receiver!!.id != memberId) {
            throw NotificationException(NotificationErrorCode.NOTIFICATION_ACCESS_DENIED)
        }
        noti.viewedAt = LocalDateTime.now()
        notificationRepository.save(noti)
    }
}