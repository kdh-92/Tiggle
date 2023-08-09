package com.side.tiggle.domain.transaction.model;

import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE transactions SET deleted_at = CURRENT_TIMESTAMP, deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
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

    @Column(name = "asset_id", nullable = false)
    private Long assetId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "tag_names", nullable = false)
    private String tagNames;



    @OneToMany(mappedBy = "tx", fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @Builder
    public Transaction(Long memberId, Long parentId, TransactionType type, String imageUrl, Integer amount, LocalDate date, String content, String reason, Long assetId, Long categoryId, String tagNames) {
        this.memberId = memberId;
        this.parentId = parentId;
        this.type = type;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.date = date;
        this.content = content;
        this.reason = reason;
        this.assetId = assetId;
        this.categoryId = categoryId;
        this.tagNames = tagNames;
    }
}
