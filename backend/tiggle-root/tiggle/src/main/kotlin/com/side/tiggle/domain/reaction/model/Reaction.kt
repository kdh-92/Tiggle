package com.side.tiggle.domain.reaction.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
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

    @JsonIgnore
    @JoinColumn(name = "tx_id", nullable = false)
    @ManyToOne
    var tx: Transaction,

    @JsonIgnore
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var sender: Member,

    @JsonIgnore
    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    var receiver: Member
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()
}