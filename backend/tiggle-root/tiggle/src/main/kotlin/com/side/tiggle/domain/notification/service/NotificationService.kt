package com.side.tiggle.domain.notification.service

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.transaction.model.Transaction

interface NotificationService {

    fun getAllByMemberId(memberId: Long): List<NotificationRespDto>

    fun sendCommentNotification(
        comment: Comment,
        parentComment: Comment?,
        tx: Transaction,
        sender: Member
    )

    fun readNotificationById(memberId: Long, id: Long)
}