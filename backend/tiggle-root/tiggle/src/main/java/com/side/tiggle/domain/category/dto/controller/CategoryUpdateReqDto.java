package com.side.tiggle.domain.category.dto.controller;

import com.side.tiggle.domain.category.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class CategoryUpdateReqDto extends CategoryDto {

}
