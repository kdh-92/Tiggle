package com.side.tiggle.domain.category.service;

import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.category.repository.CategoryRepository;
import com.side.tiggle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException());
    }
}
