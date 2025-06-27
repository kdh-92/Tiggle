package com.side.tiggle.domain.notification.service

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.notification.NotificationProducer
import com.side.tiggle.domain.notification.dto.resp.NotificationRespDto
import com.side.tiggle.domain.notification.dto.NotificationProduceDto
import com.side.tiggle.domain.notification.repository.NotificationRepository
import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val notificationProducer: NotificationProducer,
    private val memberService: MemberService,
) {

    fun getAllByMemberId(memberId: Long): List<NotificationRespDto> {
        val member = memberService.getMemberOrThrow(memberId)
        val notiList = notificationRepository.findAllByReceiver(member)
        return notiList.map {
            val sender = if (it.sender != null) {
                MemberRespDto.fromEntity(it.sender!!)
            } else {
                null
            }
            val receiver = MemberRespDto.fromEntity(it.receiver!!)
            NotificationRespDto.fromEntity(it)
                .apply {
                    this.sender = sender
                    this.receiver = receiver
                    this.comment = CommentRespDto.fromEntity(it.comment!!)
                }
        }
    }

    fun sendCommentNotification(
        comment: Comment,
        parentComment: Comment?,
        tx: Transaction,
        sender: Member
    ) {
        val receiver = parentComment?.sender ?: tx.member
        val type = if (parentComment != null) NotificationProduceDto.Type.REPLY else NotificationProduceDto.Type.COMMENT

        notificationProducer.send(
            NotificationProduceDto(
                imageUrl = null,
                content = comment.content,
                receiverId = receiver.id,
                senderId = sender.id,
                title = tx.content,
                txId = tx.id!!,
                commentId = comment.id,
                type = type
            )
        )
    }

    fun readNotificationById(memberId: Long, id: Long) {
        val noti = notificationRepository.findById(id).orElseThrow {
            NotFoundException()
        }
        if (noti.receiver!!.id != memberId) {
            throw NotFoundException()
        }
        noti.viewedAt = LocalDateTime.now()
        notificationRepository.save(noti)
    }
}