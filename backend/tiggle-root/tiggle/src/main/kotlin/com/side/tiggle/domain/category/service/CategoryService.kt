package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.exception.CategoryException
import com.side.tiggle.domain.category.exception.error.CategoryErrorCode
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.member.service.MemberService
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val memberService: MemberService
) {
    fun createCategory(dto: CategoryCreateReqDto, memberId: Long): CategoryRespDto {
        val member = memberService.getMemberOrThrow(memberId)
        val category = dto.toEntity(member)
        return CategoryRespDto.fromEntity(categoryRepository.save(category))
    }

    fun getCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }
    }

    fun getCategoryByMemberIdOrDefaults(memberId: Long): CategoryListRespDto {
        val categories = categoryRepository.findCategoryByMemberIdOrDefaults(memberId, true)
        val dtoList = categories.map { CategoryRespDto.fromEntity(it) }
        return CategoryListRespDto(dtoList)
    }

    fun updateCategory(id: Long, dto: CategoryUpdateReqDto): CategoryRespDto {
        val category = categoryRepository.findById(id)
            .orElseThrow { CategoryException(CategoryErrorCode.CATEGORY_NOT_FOUND) }

        category.apply {
            name = dto.name
        }

        return CategoryRespDto.fromEntity(categoryRepository.save(category))
    }

    fun deleteCategory(categoryId: Long) {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException() }

        category.apply {
            deleted = true
            deletedAt = LocalDateTime.now()
        }

        categoryRepository.save(category)
    }
}
