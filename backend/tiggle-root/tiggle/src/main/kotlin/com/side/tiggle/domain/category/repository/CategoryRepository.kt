package com.side.tiggle.domain.category.repository

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.category.model.CategoryType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository: JpaRepository<Category, Long> {
    fun findByType(type: CategoryType): List<Category>
}