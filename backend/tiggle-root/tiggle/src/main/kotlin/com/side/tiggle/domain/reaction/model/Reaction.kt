package com.side.tiggle.domain.reaction.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import jakarta.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Table(name = "reactions")
class Reaction(
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    var type: ReactionType,
    var txId: Long,
    var senderId: Long,
    var receiverId: Long
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
}