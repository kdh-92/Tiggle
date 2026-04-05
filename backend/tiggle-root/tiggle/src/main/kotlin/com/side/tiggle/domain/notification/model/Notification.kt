package com.side.tiggle.domain.notification.model

import com.side.tiggle.domain.comment.model.Comment
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@Table(name = "notifications")
class Notification(
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: NotificationType,
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    var comment: Comment? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tx_id")
    var tx: Transaction? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    var receiver: Member? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    var sender: Member? = null

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
    @Column(name = "viewed_at")
    var viewedAt: LocalDateTime? = null
}