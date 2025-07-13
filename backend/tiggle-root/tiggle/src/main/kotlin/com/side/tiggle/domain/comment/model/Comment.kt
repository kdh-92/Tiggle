package com.side.tiggle.domain.comment.model

import com.side.tiggle.domain.member.model.Member
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    val sender: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    val receiver: Member,

    @Column(name = "content", nullable = false)
    var content: String,

    @Column(name = "parent_id")
    var parentId: Long? = null
): BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
