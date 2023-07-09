package com.side.tiggle.domain.comment.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "tx_id", nullable = false)
    private Long txId;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Comment(Long txId, Long senderId, Long receiverId, String content) {
        this.txId = txId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
    }
}
