package com.side.tiggle.tigglenotification.notification.model

data class NotificationDto(
    val imageUrl: String? = null,
    val title: String,
    val content: String,
    val receiverId: Long,
    val senderId: Long?,
    val commentId: Long?,
    val txId: Long?,
    val type: Notification.Type
) {
    fun toEntity(): Notification {
        return Notification(
            receiverId = receiverId,
            senderId = senderId,
            txId = txId,
            commentId = commentId,
            type = type,
            title = title,
            content = content,
            imageUrl = imageUrl)
    }

    companion object {
        fun fromEntity(entity: Notification): NotificationDto {
            return NotificationDto(
                receiverId = entity.receiverId,
                senderId = entity.senderId,
                type = entity.type,
                content = entity.content ?: "",
                title = entity.title ?: "",
                txId = entity.txId,
                commentId = entity.commentId,
                imageUrl = entity.imageUrl
            )
        }
    }
}
