package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.exception.CategoryException
import com.side.tiggle.domain.category.exception.error.CategoryErrorCode
import com.side.tiggle.domain.category.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {

    override fun createCategory(dto: CategoryCreateReqDto, memberId: Long) {
        val category = dto.toEntity(memberId)
        categoryRepository.save(category)
    }

    override fun getCategoryByMemberIdOrDefaults(memberId: Long): CategoryListRespDto {
        val categories = categoryRepository.findCategoryByMemberIdOrDefaults(memberId, true)
        val dtoList = categories.map { CategoryRespDto.fromEntity(it) }
        return CategoryListRespDto(dtoList)
    }

    override fun updateCategory(id: Long, dto: CategoryUpdateReqDto) {
        val category = categoryRepository.findById(id)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        category.apply {
            name = dto.name
        }

        categoryRepository.save(category)
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
