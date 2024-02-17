package com.side.tiggle.domain.notification.service

import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.member.dto.service.MemberDto
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.notification.dto.NotificationDto
import com.side.tiggle.domain.notification.model.Notification
import com.side.tiggle.domain.notification.repository.NotificationRepository
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val memberService: MemberService,
) {

    fun getAllByMemberId(memberId: Long): List<NotificationDto> {
        val member = memberService.getMember(memberId)
        val notiList = notificationRepository.findAllByReceiver(member)
        return notiList.map {
            val sender = if (it.sender != null) {
                MemberDto.fromEntityToMemberResponseDto(it.sender!!)
            } else {
                null
            }
            val receiver = MemberDto.fromEntityToMemberResponseDto(it.receiver!!)
            NotificationDto.fromEntity(it)
                .apply {
                    this.sender = sender
                    this.receiver = receiver
                    this.comment = CommentRespDto.fromEntity(it.comment!!)
                }
        }
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