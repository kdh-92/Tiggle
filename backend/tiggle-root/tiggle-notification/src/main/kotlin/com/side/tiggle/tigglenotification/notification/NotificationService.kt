package com.side.tiggle.tigglenotification.notification

import com.side.tiggle.tigglenotification.notification.model.Notification
import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import com.side.tiggle.tigglenotification.notification.repository.NotificationRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
        private val notificationRepository: NotificationRepository
) {
    fun getAllByMemberId(memberId: Long): List<NotificationDto> {
        val noti = notificationRepository.findAllByReceiverId(memberId)
        return noti.map { NotificationDto.fromEntity(it) }
    }

    fun getById(memberId: Long, id: Long): Notification {
        val noti = notificationRepository.findById(id).orElseThrow {
            RuntimeException("Not Found")
        }
        if (noti.receiverId != memberId) {
            throw RuntimeException("Not Authorized")
        }
        return noti
    }

    fun readNotification(memberId: Long, id: Long) {
        val noti = this.getById(memberId, id)
        noti.viewedAt = LocalDateTime.now()
        notificationRepository.save(noti)
    }

    fun save(dto: NotificationDto) {
        notificationRepository.save(dto.toEntity())
    }
}
