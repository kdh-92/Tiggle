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
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CategoryServiceImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryService {

    @Transactional
    override fun createCategory(dto: CategoryCreateReqDto, memberId: Long) {
        val category = dto.toEntity(memberId)
        categoryRepository.save(category)
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

    @Transactional
    override fun updateCategory(id: Long, dto: CategoryUpdateReqDto) {
        val category = categoryRepository.findById(id)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        category.apply {
            name = dto.name
        }

        categoryRepository.save(category)
    }

    @Transactional
    override fun deleteCategory(categoryId: Long) {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        category.apply {
            deleted = true
            deletedAt = LocalDateTime.now()
        }

        categoryRepository.save(category)
    }

    /**
     * 거래(transaction) 생성 시 연관관계 설정을 위한
     * Category 프록시 객체를 반환합니다.
     *
     * @param categoryId 카테고리 ID
     * @return Category 프록시 객체 (실제 DB 조회 없이 연관관계 설정용)
     * @since 2025-07-04
     * @author 양병학
     */
    override fun getCategoryReference(categoryId: Long): Category {
        return categoryRepository.getReferenceById(categoryId)
    }
}
