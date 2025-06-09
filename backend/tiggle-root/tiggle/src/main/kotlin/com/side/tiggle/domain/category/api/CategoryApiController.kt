package com.side.tiggle.domain.category.api

import com.side.tiggle.domain.category.dto.CategoryDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.side.tiggle.global.common.constants.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/category")
class CategoryApiController(
    private val categoryService: CategoryService
) {

    @PostMapping
    fun createCategory(
        @RequestBody categoryDto: CategoryDto,
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<CategoryRespDto> {
        val categoryDtoWithMemberId = categoryDto.copy(
            memberId = memberId,
            defaults = false // 기본값은 false로 설정
        )
        return ResponseEntity(
            CategoryRespDto.fromEntity(
                categoryService.createCategory(categoryDtoWithMemberId)
            ), HttpStatus.CREATED
        )
    }

    @GetMapping("/all")
    fun getAllCategory(): ResponseEntity<List<CategoryRespDto>> {
        return ResponseEntity(
            categoryService.getAllCategory()
                .map { CategoryRespDto.fromEntity(it) }
            , HttpStatus.OK
        )
    }

    @GetMapping()
    fun getCategory(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<List<CategoryRespDto>> {
        return ResponseEntity(
            categoryService.getCategoryByMemberIdOrDefaults(memberId)
                .map { CategoryRespDto.fromEntity(it) }
            , HttpStatus.OK
        )
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable("id") categoryId: Long,
        @RequestBody dto: CategoryDto
    ): ResponseEntity<CategoryRespDto> {
        return ResponseEntity(
            CategoryRespDto.fromEntity(
                categoryService.updateCategory(categoryId, dto)
            ), HttpStatus.OK
        )
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable("id") categoryId: Long
    ): ResponseEntity<Nothing> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity(null, HttpStatus.OK)
    }
}
