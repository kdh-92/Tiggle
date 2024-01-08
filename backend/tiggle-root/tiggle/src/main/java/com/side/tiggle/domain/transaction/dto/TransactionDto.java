package com.side.tiggle.domain.transaction.dto;

import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.model.TransactionType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@AllArgsConstructor
public class TransactionDto {

    @JsonIgnore
    private Long memberId;
    private Long parentId;
    private Long assetId;
    private Long categoryId;
    private TransactionType type;
    private String imageUrl;
    private int amount;
    private LocalDate date;
    private String content;
    private String reason;


    private String tagNames;

    public Transaction toEntity(Member member, Asset asset, Category category) {
        return Transaction.builder()
                .member(member)
                .asset(asset)
                .category(category)
                .parentId(parentId)
                .type(type)
                .imageUrl(imageUrl)
                .amount(amount)
                .date(date)
                .content(content)
                .reason(reason)
                .tagNames(tagNames)
                .build();
    }

    public static TransactionDto fromEntity(Transaction tx) {
        return TransactionDto.builder()
                .memberId(tx.getMember().getId())
                .parentId(tx.getParentId())
                .assetId(tx.getAsset().getId())
                .categoryId(tx.getCategory().getId())
                .type(tx.getType())
                .imageUrl(tx.getImageUrl())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .content(tx.getContent())
                .reason(tx.getReason())
                .build();
    }
}
