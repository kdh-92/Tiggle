package com.side.tiggle.domain.transaction.dto.resp;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.transaction.dto.TransactionDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.txtag.TxTagDto;
import com.side.tiggle.domain.txtag.model.TxTag;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TransactionRespDto extends TransactionDto {

    private Long id;
    private TransactionDto parentTx;
    private LocalDateTime createdAt;
    private AssetDto asset;
    private CategoryDto category;
    private TxTagDto txTag;


    public static TransactionRespDto fromEntity(Transaction tx) {
        return TransactionRespDto.builder()
                .id(tx.getId())
                .memberId(tx.getMemberId())
                .parentId(tx.getParentId())
                .type(tx.getType())
                .imageUrl(tx.getImageUrl())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .content(tx.getContent())
                .reason(tx.getReason())
                .createdAt(tx.getCreatedAt())
                .build();
    }

    public static TransactionRespDto fromEntityDetailTx(Transaction tx, Asset asset, Category category, TxTag txTag) {
        return TransactionRespDto.builder()
                .id(tx.getId())
                .memberId(tx.getMemberId())
                .parentId(tx.getParentId())
                .type(tx.getType())
                .imageUrl(tx.getImageUrl())
                .amount(tx.getAmount())
                .date(tx.getDate())
                .content(tx.getContent())
                .reason(tx.getReason())
                .asset(AssetDto.fromEntity(asset))
                .category(CategoryDto.fromEntity(category))
                .txTag(TxTagDto.fromEntity(txTag))
                .createdAt(tx.getCreatedAt())
                .build();
    }

    public static TransactionRespDto fromEntityParentTx(Transaction refundTx, Transaction parentTx) {
        return TransactionRespDto.builder()
                .id(refundTx.getId())
                .memberId(refundTx.getMemberId())
                .parentId(refundTx.getParentId())
                .type(refundTx.getType())
                .imageUrl(refundTx.getImageUrl())
                .amount(refundTx.getAmount())
                .date(refundTx.getDate())
                .content(refundTx.getContent())
                .reason(refundTx.getReason())
                .createdAt(refundTx.getCreatedAt())
                .parentTx(TransactionDto.fromEntity(parentTx))
                .build();
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