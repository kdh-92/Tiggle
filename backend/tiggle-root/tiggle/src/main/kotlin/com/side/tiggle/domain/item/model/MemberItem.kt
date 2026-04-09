package com.side.tiggle.domain.item.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "member_items",
    uniqueConstraints = [UniqueConstraint(columnNames = ["member_id", "item_id"])]
)
class MemberItem(
    @Column(name = "member_id", nullable = false)
    val memberId: Long,

    @Column(name = "item_id", nullable = false)
    val itemId: Long,

    @Column(name = "acquired_at", nullable = false)
    val acquiredAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "acquire_reason", length = 200)
    val acquireReason: String? = null
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
