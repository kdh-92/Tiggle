package com.side.tiggle.domain.category.service;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.dto.controller.CategoryUpdateReqDto;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.category.model.CategoryType;
import com.side.tiggle.domain.category.repository.CategoryRepository;
import com.side.tiggle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto dto) {
        // 기존 카테고리 비교 추가
        Category category = Category.builder()
                .name(dto.getName())
                .type(dto.getType())
                .defaults(dto.getDefaults())
                .build();

        return categoryRepository.save(category);
    }

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(NotFoundException::new);
    }

    public List<Category> getCategoryType(CategoryType type) {
        return categoryRepository.findByType(type);
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category updateCategory(Long id, CategoryUpdateReqDto dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        category.setType(dto.getType());
        category.setName(dto.getName());
        category.setDefaults(dto.getDefaults());
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
