package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.CategoryDto
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun createCategory(dto: CategoryDto): Category {
        return categoryRepository.save(dto.toEntity())
    }

    fun getCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException() }
    }

    fun getAllCategory(): List<Category> {
        return categoryRepository.findAll()
    }

    fun updateCategory(id: Long, dto: CategoryDto): Category {
        val category = categoryRepository.findById(id)
            .orElseThrow { NotFoundException() }
        category.apply {
            name = dto.name
            defaults = dto.defaults
        }
        return categoryRepository.save(category)
    }

    fun deleteCategory(categoryId: Long) {
        categoryRepository.deleteById(categoryId)
    }
}