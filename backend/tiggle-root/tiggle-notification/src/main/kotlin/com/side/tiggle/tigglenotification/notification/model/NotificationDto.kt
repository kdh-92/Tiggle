package com.side.tiggle.tigglenotification.notification.model

data class NotificationDto(
        val receiverId: Long,
        val senderId: Long?,
        val type: Notification.Type,
        val content: String
) {

    fun toEntity(): Notification {
        return Notification(receiverId, senderId, type, content)
    }
}