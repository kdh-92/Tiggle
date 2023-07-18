package com.side.tiggle.domain.comment.model;

import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE comments SET deleted_at = CURRENT_TIMESTAMP, deleted = true where id = ?")
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "tx_id", nullable = false)
    @ManyToOne
    private Transaction tx;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Comment(Transaction tx, Long parentId, Long senderId, Long receiverId, String content) {
        this.tx = tx;
        this.parentId = parentId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }
}
