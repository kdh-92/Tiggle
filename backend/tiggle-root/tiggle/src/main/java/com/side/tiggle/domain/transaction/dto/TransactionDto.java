package com.side.tiggle.domain.transaction.dto;

import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.model.TransactionType;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@AllArgsConstructor
public class TransactionDto {

    @NotBlank(message = "member 입력이 필요합니다.")
    private Long memberId;
    private Long parentId;
    private TransactionType type;
    private String imageUrl;
    private int amount;
    private LocalDate date;
    private String content;
    private String reason;

    private Long assetId;

    private Long categoryId;

    private String tagNames;

    public Transaction toEntity(Member member) {
        return Transaction.builder()
                .member(member)
                .parentId(parentId)
                .type(type)
                .imageUrl(imageUrl)
                .amount(amount)
                .date(date)
                .content(content)
                .reason(reason)
                .assetId(assetId)
                .categoryId(categoryId)
                .tagNames(tagNames)
                .build();
    }

    public static TransactionDto fromEntity(Transaction tx) {
        return TransactionDto.builder()
                .memberId(tx.getMember().getId())
                .parentId(tx.getParentId())
                .type(tx.getType())
                .imageUrl(tx.getImageUrl())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .content(tx.getContent())
                .reason(tx.getReason())
                .build();
    }
}
