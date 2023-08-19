package com.side.tiggle.domain.transaction.dto.resp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.member.dto.MemberDto;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.dto.TransactionDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TransactionRespDto extends TransactionDto {

    private Long id;
    private MemberDto member;
    private TransactionDto parentTx;
    private AssetDto asset;
    private CategoryDto category;
    private String txTagNames;
    private LocalDateTime createdAt;

    @Override
    @JsonIgnore
    public Long getMemberId() {
        return super.getMemberId();
    }

    @Override
    @JsonIgnore
    public Long getAssetId() {
        return super.getAssetId();
    }

    @Override
    @JsonIgnore
    public Long getCategoryId() {
        return super.getCategoryId();
    }

    @Override
    @JsonIgnore
    public String getTagNames() {
        return super.getTagNames();
    }

    public static TransactionRespDto fromEntity(Transaction tx) {
        return TransactionRespDto.builder()
                .id(tx.getId())
                .parentId(tx.getParentId())
                .member(MemberDto.fromEntity(tx.getMember()))
                .type(tx.getType())
                .imageUrl(tx.getImageUrl())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .content(tx.getContent())
                .reason(tx.getReason())
                .createdAt(tx.getCreatedAt())
                .build();
    }

    public static TransactionRespDto fromEntityDetailTx(Transaction tx, Transaction parentTx, Asset asset, Category category) {
        TransactionRespDtoBuilder builder = TransactionRespDto.builder()
                .id(tx.getId())
                .member(MemberDto.fromEntity(tx.getMember()))
                .type(tx.getType())
                .imageUrl(tx.getImageUrl())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .content(tx.getContent())
                .reason(tx.getReason())
                .asset(AssetDto.fromEntity(asset))
                .category(CategoryDto.fromEntity(category))
                .txTagNames(tx.getTagNames())
                .createdAt(tx.getCreatedAt());

        if (parentTx != null) builder.parentTx(TransactionDto.fromEntity(parentTx));

        return builder.build();
    }

    public static Page<TransactionRespDto> fromEntityPage(Page<Transaction> txPage) {
        return new PageImpl<>(
                txPage.getContent()
                        .stream()
                        .map(TransactionRespDto::fromEntity)
                        .collect(Collectors.toList()),
                txPage.getPageable(),
                txPage.getTotalElements()
        );
    }
}