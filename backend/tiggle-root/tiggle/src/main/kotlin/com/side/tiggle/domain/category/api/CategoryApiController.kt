package com.side.tiggle.domain.category.api

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.global.common.ApiResponse
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
        @RequestBody categoryCreateReqDto: CategoryCreateReqDto,
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<CategoryRespDto>> {
        val createdCategory = categoryService.createCategory(categoryCreateReqDto, memberId)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(createdCategory))
    }

    @GetMapping()
    fun getCategoryByMemberIdOrDefaults(
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long
    ): ResponseEntity<ApiResponse<CategoryListRespDto>> {
        val categories = categoryService.getCategoryByMemberIdOrDefaults(memberId)
        return ResponseEntity
            .ok(ApiResponse.success(categories))
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable("id") categoryId: Long,
        @RequestBody dto: CategoryUpdateReqDto
    ): ResponseEntity<ApiResponse<CategoryRespDto>> {
        val updatedCategory = categoryService.updateCategory(categoryId, dto)
        return ResponseEntity
            .ok(ApiResponse.success(updatedCategory))
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable("id") categoryId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity
            .ok(ApiResponse.success(null))
    }
}
