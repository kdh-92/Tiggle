package com.side.tiggle.domain.category.service

import com.side.tiggle.domain.category.dto.req.CategoryCreateReqDto
import com.side.tiggle.domain.category.dto.req.CategoryUpdateReqDto
import com.side.tiggle.domain.category.dto.resp.CategoryListRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.category.model.Category

interface CategoryService {

    fun createCategory(dto: CategoryCreateReqDto, memberId: Long): CategoryRespDto

    fun getCategory(categoryId: Long): CategoryRespDto

    fun getCategoryEntity(categoryId: Long): Category

    fun getCategoryByMemberIdOrDefaults(memberId: Long): CategoryListRespDto

    fun updateCategory(id: Long, dto: CategoryUpdateReqDto): CategoryRespDto

    fun deleteCategory(categoryId: Long)
}