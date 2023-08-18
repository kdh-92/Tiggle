package com.side.tiggle.domain.transaction.dto.resp;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.member.dto.MemberDto;
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class TransactionUpdateRespDto extends TransactionRespDto{

    public static TransactionUpdateRespDto fromEntity(Transaction tx, Asset asset, Category category) {
        return TransactionUpdateRespDto.builder()
                .id(tx.getId())
                .parentId(tx.getParentId())
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
                .createdAt(tx.getCreatedAt())
                .build();
    }
}
