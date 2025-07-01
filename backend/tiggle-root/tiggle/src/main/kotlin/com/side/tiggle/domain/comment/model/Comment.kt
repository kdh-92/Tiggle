package com.side.tiggle.domain.comment.model

import com.side.tiggle.global.common.model.BaseEntity
import org.hibernate.annotations.SQLDelete
import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

@Entity
@SQLRestriction("deleted = false")
@SQLDelete(sql = "UPDATE comments SET deleted_at = CURRENT_TIMESTAMP, deleted = true where id = ?")
@Table(name = "comments")
class Comment(
    @Column(name = "tx_id", nullable = false)
    val txId: Long,

    @Column(name = "sender_id", nullable = false)
    val senderId: Long,

    @Column(name = "receiver_id", nullable = false)
    val receiverId: Long,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "parent_id")
    var parentId: Long? = null
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
