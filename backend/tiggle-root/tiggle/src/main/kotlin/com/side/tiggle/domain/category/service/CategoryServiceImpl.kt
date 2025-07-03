package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.exception.CategoryException
import com.side.tiggle.domain.category.exception.error.CategoryErrorCode
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {

    override fun createCategory(dto: CategoryCreateReqDto, memberId: Long): CategoryRespDto {
        val category = dto.toEntity(memberId)
        return CategoryRespDto.fromEntity(categoryRepository.save(category))
    }

    override fun getCategory(categoryId: Long): CategoryRespDto {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        return CategoryRespDto.fromEntity(category)
    }

    override fun getCategoryEntity(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }
    }

    override fun getCategoryByMemberIdOrDefaults(memberId: Long): CategoryListRespDto {
        val categories = categoryRepository.findCategoryByMemberIdOrDefaults(memberId, true)
        val dtoList = categories.map { CategoryRespDto.fromEntity(it) }
        return CategoryListRespDto(dtoList)
    }

    override fun updateCategory(id: Long, dto: CategoryUpdateReqDto): CategoryRespDto {
        val category = categoryRepository.findById(id)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        category.apply {
            name = dto.name
        }

        return CategoryRespDto.fromEntity(categoryRepository.save(category))
    }

    override fun deleteCategory(categoryId: Long) {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        category.apply {
            deleted = true
            deletedAt = LocalDateTime.now()
        }

        categoryRepository.save(category)
    }
}