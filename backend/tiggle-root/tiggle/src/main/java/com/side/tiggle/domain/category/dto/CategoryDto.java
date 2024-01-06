package com.side.tiggle.domain.category.dto;

import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.category.model.CategoryType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class CategoryDto {

    private String name;
    private CategoryType type;
    private Boolean defaults;

    public static CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .name(category.getName())
                .type(category.getType())
                .defaults(category.isDefaults())
                .build();
    }
}
