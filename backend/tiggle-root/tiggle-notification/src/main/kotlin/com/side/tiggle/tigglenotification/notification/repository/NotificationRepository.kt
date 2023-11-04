package com.side.tiggle.tigglenotification.notification.repository

import com.side.tiggle.tigglenotification.notification.model.Notification
import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository: JpaRepository<Notification, Long> {
    fun findAllByReceiverId(receiverId: Long): List<Notification>
}