package com.side.tiggle.domain.category.api

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.service.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import com.side.tiggle.global.common.constants.HttpHeaders
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/category")
class CategoryApiController(
    private val categoryService: CategoryService
) {

    @PostMapping
    fun createCategory(
        @RequestBody @Valid categoryCreateReqDto: CategoryCreateReqDto,
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<CategoryRespDto> {
        val createdCategory = categoryService.createCategory(categoryCreateReqDto, memberId)
        return ResponseEntity(createdCategory, HttpStatus.CREATED)
    }

    @GetMapping()
    fun getCategoryByMemberIdOrDefaults(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<CategoryListRespDto> {
        val categories = categoryService.getCategoryByMemberIdOrDefaults(memberId)
        return ResponseEntity(categories, HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable("id") @Min(1) categoryId: Long,
        @RequestBody @Valid dto: CategoryUpdateReqDto
    ): ResponseEntity<CategoryRespDto> {
        val updatedCategory = categoryService.updateCategory(categoryId, dto)
        return ResponseEntity(updatedCategory, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable("id") @Min(1) categoryId: Long
    ): ResponseEntity<Void> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity(null, HttpStatus.OK)
    }
}
