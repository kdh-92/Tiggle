package com.side.tiggle.domain.transaction.dto;

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
    @NotBlank(message = "이미지 입력이 필요합니다.")
    private String imageUrl;
    private int amount;
    private LocalDate date;
    private String content;
    private String reason;

    private Long assetId;

    private Long categoryId;

    private String tagNames;

    public Transaction toEntity(TransactionDto dto) {
        return Transaction.builder()
                .memberId(dto.getMemberId())
                .parentId(dto.getParentId())
                .type(dto.getType())
                .imageUrl(dto.getImageUrl())
                .amount(dto.getAmount())
                .date(dto.getDate())
                .content(dto.getContent())
                .reason(dto.getReason())
                .assetId(dto.getAssetId())
                .categoryId(dto.getCategoryId())
                .tagNames(dto.getTagNames())
                .build();
    }

    public static TransactionDto fromEntity(Transaction tx) {
        return TransactionDto.builder()
                .memberId(tx.getMemberId())
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
