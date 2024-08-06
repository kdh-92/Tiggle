package com.side.tiggle.domain.comment.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.global.common.model.BaseEntity
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.Where
import javax.persistence.*

@Entity
@Where(clause = "deleted = false")
@SQLDelete(sql = "UPDATE comments SET deleted_at = CURRENT_TIMESTAMP, deleted = true where id = ?")
@Table(name = "comments")
class Comment(
    @JsonIgnore
    @JoinColumn(name = "tx_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val tx: Transaction,

    @JsonIgnore
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    val sender: Member,

    @JsonIgnore
    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
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