package com.side.tiggle.domain.category.service;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.model.Asset;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.category.model.CategoryType;
import com.side.tiggle.domain.category.repository.CategoryRepository;
import com.side.tiggle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto dto) {
        Category category = Category.builder()
                .name(dto.getName())
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
}
