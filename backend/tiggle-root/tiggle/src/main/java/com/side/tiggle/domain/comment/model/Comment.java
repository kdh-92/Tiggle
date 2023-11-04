package com.side.tiggle.domain.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.side.tiggle.domain.member.model.Member;
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

    @JsonIgnore
    @JoinColumn(name = "tx_id", nullable = false)
    @ManyToOne
    private Transaction tx;

    @Column(name = "parent_id")
    private Long parentId;

    @JsonIgnore
    @JoinColumn(name = "sender_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member sender;

    @JsonIgnore
    @JoinColumn(name = "receiver_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member receiver;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Comment(Transaction tx, Member sender, Long parentId, Member receiver, String content) {
        this.tx = tx;
        this.sender = sender;
        this.parentId = parentId;
        this.receiver = receiver;
        this.content = content;
    }
}
