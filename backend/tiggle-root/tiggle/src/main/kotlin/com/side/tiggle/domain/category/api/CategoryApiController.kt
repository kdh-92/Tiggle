package com.side.tiggle.domain.category.api

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.global.common.ApiResponse
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
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.createCategory(categoryCreateReqDto, memberId)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(null, message = "카테고리가 생성되었습니다."))
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
        @PathVariable("id") @Min(1) categoryId: Long,
        @RequestBody @Valid dto: CategoryUpdateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.updateCategory(categoryId, dto)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "카테고리가 수정되었습니다."))
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(
        @PathVariable("id") @Min(1) categoryId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        categoryService.deleteCategory(categoryId)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "카테고리가 삭제되었습니다."))
    }
}
