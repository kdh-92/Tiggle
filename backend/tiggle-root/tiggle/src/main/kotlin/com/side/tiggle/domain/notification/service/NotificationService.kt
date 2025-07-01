package com.side.tiggle.domain.notification.service

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo

interface NotificationService {

    fun getAllByMemberId(memberId: Long): List<NotificationRespDto>

    fun sendCommentNotification(
        comment: Comment,
        parentComment: Comment?,
        tx: TransactionInfo,
        senderId: Long
    )

    fun readNotificationById(memberId: Long, id: Long)
}