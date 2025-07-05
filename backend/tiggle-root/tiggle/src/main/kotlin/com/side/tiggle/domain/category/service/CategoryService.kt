package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto

interface CategoryService {

    fun createCategory(dto: CategoryCreateReqDto, memberId: Long)
    fun getCategoryByMemberIdOrDefaults(memberId: Long): CategoryListRespDto
    fun updateCategory(id: Long, dto: CategoryUpdateReqDto)
    fun deleteCategory(categoryId: Long)
}
