package com.side.tiggle.tigglenotification.notification.model

data class NotificationDto(
    val imageUrl: String? = null,
    val title: String,
    val content: String,
    val receiverId: Long,
    val senderId: Long?,
    val type: Notification.Type
) {

    fun toEntity(): Notification {
        return Notification(receiverId, senderId, type, title, content, imageUrl)
    }

    companion object {
        fun fromEntity(entity: Notification): NotificationDto {
            return NotificationDto(
                receiverId = entity.receiverId,
                senderId = entity.senderId,
                type = entity.type,
                content = entity.content ?: "",
                title = entity.title ?: "",
                imageUrl = entity.imageUrl
            )
        }
    }
}
