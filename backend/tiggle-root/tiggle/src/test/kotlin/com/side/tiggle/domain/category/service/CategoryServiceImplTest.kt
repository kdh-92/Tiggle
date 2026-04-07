
package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.exception.CategoryException
import com.side.tiggle.domain.category.exception.error.CategoryErrorCode
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.repository.CategoryRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDateTime
import java.util.Optional

class CategoryServiceImplTest : StringSpec({

    val categoryRepository: CategoryRepository = mockk()
    val categoryService: CategoryService = CategoryServiceImpl(categoryRepository)

    "카테고리를 생성합니다" {
        // given
        val memberId = 1L
        val dto = CategoryCreateReqDto(name = "Study")
        val expected = Category(name = dto.name, defaults = false, memberId = memberId)

        val capturedSlot = slot<Category>()
        every { categoryRepository.save(capture(capturedSlot)) } returns expected

        // when
        categoryService.createCategory(dto, memberId)

        // then
        verify(exactly = 1) { categoryRepository.save(any()) }
        capturedSlot.captured.name shouldBe "Study"
        capturedSlot.captured.memberId shouldBe 1L
        capturedSlot.captured.defaults shouldBe false
    }


    "카테고리 ID로 조회 시 존재하면 반환한다" {
        val category = Category(name = "Work", defaults = false, memberId = 1L)
        every { categoryRepository.findById(1L) } returns Optional.of(category)

        val result = categoryService.getCategoryEntity(1L)

        result shouldBe category
    }

    "카테고리 ID로 조회 시 없으면 예외를 던진다" {
        every { categoryRepository.findById(1L) } returns Optional.empty()

        val exception = shouldThrow<CategoryException> {
            categoryService.getCategoryEntity(1L)
        }

        exception.getErrorCode() shouldBe CategoryErrorCode.CATEGORY_NOT_FOUND
    }

    "회원 ID로 기본 또는 사용자 지정 카테고리 목록을 조회한다" {
        val category = Category(name = "Default", defaults = true, memberId = 1L)
        val categories = listOf(category)

        every { categoryRepository.findCategoryByMemberIdOrDefaults(1L, true) } returns categories

        val result = categoryService.getCategoryByMemberIdOrDefaults(1L)

        result.categories.size shouldBe 1
        result.categories[0].name shouldBe "Default"
    }

    "카테고리 이름을 수정한다" {
        val memberId = 1L
        val existing = Category(name = "Old", defaults = false, memberId = memberId)
        val dto = CategoryUpdateReqDto(name = "New")

        every { categoryRepository.findById(1L) } returns Optional.of(existing)
        every { categoryRepository.save(existing) } returns existing

        categoryService.updateCategory(1L, memberId, dto)

        existing.name shouldBe "New"
        verify { categoryRepository.save(existing) }
    }

    "카테고리 수정 시 존재하지 않는 ID라면 예외를 던진다" {
        // given
        every { categoryRepository.findById(999L) } returns Optional.empty()

        // when & then
        shouldThrow<CategoryException> {
            categoryService.updateCategory(999L, 1L, CategoryUpdateReqDto("Updated"))
        }.getErrorCode() shouldBe CategoryErrorCode.CATEGORY_NOT_FOUND
    }

    "카테고리 수정 시 권한이 없으면 예외를 던진다" {
        // given
        val categoryId = 1L
        val ownerId = 1L
        val otherMemberId = 2L
        val existing = Category(name = "Old", defaults = false, memberId = ownerId)
        val dto = CategoryUpdateReqDto(name = "New")

        every { categoryRepository.findById(categoryId) } returns Optional.of(existing)

        // when & then
        shouldThrow<CategoryException> {
            categoryService.updateCategory(categoryId, otherMemberId, dto)
        }.getErrorCode() shouldBe CategoryErrorCode.CATEGORY_ACCESS_DENIED
    }

    "카테고리를 삭제한다 (soft delete)" {
        val now = LocalDateTime.now()
        val memberId = 1L
        val category = Category(name = "DeleteMe", defaults = false, memberId = memberId)

        every { categoryRepository.findById(1L) } returns Optional.of(category)
        every { categoryRepository.save(category) } returns category

        categoryService.deleteCategory(1L, memberId)

        category.deleted shouldBe true
        category.deletedAt?.shouldBeGreaterThan(now)
        verify { categoryRepository.save(category) }
    }

    "카테고리 삭제 시 존재하지 않는 ID라면 예외를 던진다" {
        // given
        every { categoryRepository.findById(999L) } returns Optional.empty()

        // when & then
        shouldThrow<CategoryException> {
            categoryService.deleteCategory(999L, 1L)
        }.getErrorCode() shouldBe CategoryErrorCode.CATEGORY_NOT_FOUND
    }

    "카테고리 삭제 시 권한이 없으면 예외를 던진다" {
        // given
        val categoryId = 1L
        val ownerId = 1L
        val otherMemberId = 2L
        val category = Category(name = "DeleteMe", defaults = false, memberId = ownerId)

        every { categoryRepository.findById(categoryId) } returns Optional.of(category)

        // when & then
        shouldThrow<CategoryException> {
            categoryService.deleteCategory(categoryId, otherMemberId)
        }.getErrorCode() shouldBe CategoryErrorCode.CATEGORY_ACCESS_DENIED
    }

    "카테고리 ID로 getReferenceById를 호출한다" {
        val category = Category(name = "Proxy", defaults = false, memberId = 1L)
        every { categoryRepository.getReferenceById(1L) } returns category

        val result = categoryService.getCategoryReference(1L)

        result shouldBe category
    }
})
