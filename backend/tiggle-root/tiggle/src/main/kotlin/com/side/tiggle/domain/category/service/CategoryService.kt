package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.CategoryCreateDto
import com.side.tiggle.domain.category.dto.CategoryDto
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val memberService: MemberService
) {
    fun createCategory(dto: CategoryCreateDto, memberId: Long): Category {
        val member = memberService.getMember(memberId);
        return categoryRepository.save(dto.toEntity(member))
    }

    fun getCategory(categoryId: Long): Category {
        return categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException() }
    }

    fun getAllCategory(): List<Category> {
        return categoryRepository.findAll()
    }

    fun getCategoryByMemberIdOrDefaults(memberId: Long): List<Category> {
        //기본 카테코리 = true , memberId가 일치하는 카테고리 = false라서 or로 설정했습니다.
        return categoryRepository.findCategoryByMemberIdOrDefaults(memberId, true)
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

    fun deleteCategory(categoryId: Long): Category {
        val category = categoryRepository.findById(categoryId)
            .orElseThrow { NotFoundException() }
        category.apply {
            deleted = true
            deletedAt = LocalDateTime.now()
        }
        return categoryRepository.save(category)
    }
//    fun deleteCategory(categoryId: Long) {

//        categoryRepository.deleteById(categoryId)
//    }
}
