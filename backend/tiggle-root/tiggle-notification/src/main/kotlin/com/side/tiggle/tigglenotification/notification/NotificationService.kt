package com.side.tiggle.tigglenotification.notification

import com.side.tiggle.tigglenotification.notification.model.NotificationDto
import com.side.tiggle.tigglenotification.notification.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
        private val notificationRepository: NotificationRepository
) {
    fun getAllByMemberId(memberId: Long): List<NotificationDto> {
        val noti = notificationRepository.findAllByReceiverId(memberId)
        return noti.map { NotificationDto.fromEntity(it) }
    }

    fun save(dto: NotificationDto) {
        notificationRepository.save(dto.toEntity())
    }
}