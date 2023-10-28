package com.side.tiggle.domain.category.dto.resp;

import com.side.tiggle.domain.asset.dto.resp.AssetRespDto;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.model.Category;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class CategoryRespDto extends CategoryDto {

    private Long id;

    public static CategoryRespDto fromEntity(Category category) {
        return CategoryRespDto.builder()
                .id(category.getId())
                .name(category.getName())
                .defaults(category.isDefaults())
                .build();
    }
}
