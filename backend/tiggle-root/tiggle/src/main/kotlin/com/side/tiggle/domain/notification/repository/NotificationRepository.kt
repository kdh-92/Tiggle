package com.side.tiggle.domain.notification.repository

import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.notification.model.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository: JpaRepository<Notification, Long> {
    fun findAllByReceiver(receiver: Member): List<Notification>
}