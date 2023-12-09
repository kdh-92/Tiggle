package com.side.tiggle.tigglenotification.notification.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "notifications")
class Notification(
    @Column(name = "receiver_id")
    val receiverId: Long,
    @Column(name = "sender_id")
    var senderId: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: Type,
    @Column(name = "title")
    val title: String? = null,
    @Column(name = "content")
    var content: String? = null,
    @Column(name = "image_url")
    var imageUrl: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
    @Column(name = "viewed_at")
    var viewedAt: LocalDateTime? = null

    enum class Type {
        TX, COMMENT, ETC
    }
}