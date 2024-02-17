package com.side.tiggle.domain.notification.dto

data class NotificationProduceDto(
    val imageUrl: String? = null,
    val title: String,
    val content: String,
    val receiverId: Long,
    val senderId: Long?,
    val txId: Long?,
    val commentId: Long,
    val type: Type
) {
    enum class Type {
        TX, COMMENT, REPLY, ETC
    }
}