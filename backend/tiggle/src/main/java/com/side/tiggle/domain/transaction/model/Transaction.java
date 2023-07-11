package com.side.tiggle.domain.transaction.model;

import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "parent_id")
    private Long parentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Builder
    public Transaction(Long memberId, Long parentId, TransactionType type, String imageUrl, Integer amount, LocalDate date, String content, String reason) {
        this.memberId = memberId;
        this.parentId = parentId;
        this.type = type;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.date = date;
        this.content = content;
        this.reason = reason;
    }
}
