package com.side.tiggle.domain.category.repository;

import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.category.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByType(CategoryType type);
}
