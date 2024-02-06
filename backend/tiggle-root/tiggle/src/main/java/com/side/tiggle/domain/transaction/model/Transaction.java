package com.side.tiggle.domain.transaction.model;

import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.tag.model.Tag;
import com.side.tiggle.global.common.model.BaseEntity;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public Long id;

    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public Member member;

    @JsonIgnore
    @JoinColumn(name = "asset_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Asset asset;

    @JsonIgnore
    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

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

    @Column(name = "tag_names", nullable = false)
    private String tagNames;

    @OneToMany(mappedBy = "tx", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> commentList;

    @OneToMany(mappedBy = "tx", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reaction> reactionList;


    @Builder
    public Transaction(Member member, Asset asset, Category category, Long parentId, TransactionType type, String imageUrl, Integer amount, LocalDate date, String content, String reason, String tagNames) {
        this.member = member;
        this.asset = asset;
        this.category = category;
        this.parentId = parentId;
        this.type = type;
        this.imageUrl = imageUrl;
        this.amount = amount;
        this.date = date;
        this.content = content;
        this.reason = reason;
        this.tagNames = tagNames;
    }
}
