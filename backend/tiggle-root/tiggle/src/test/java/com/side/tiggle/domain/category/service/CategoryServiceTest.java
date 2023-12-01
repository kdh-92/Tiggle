package com.side.tiggle.domain.category.service;

import com.side.tiggle.domain.category.model.Category;
import com.side.tiggle.domain.category.model.CategoryType;
import com.side.tiggle.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리 생성할 수 있다.")
    @Test
    void 카테고리_생성() {
        // given
        Category category1 = createCategory("카테고리 1", CategoryType.INCOME, false);
        Category category2 = createCategory("카테고리 2", CategoryType.OUTCOME, true);
        Category category3 = createCategory("카테고리 3", CategoryType.INCOME, true);

        categoryRepository.saveAll(List.of(category1, category2, category3));

        // when 
        List<Category> categories = categoryRepository.findAll();

        // then
        assertThat(categories)
                .extracting("name", "type", "defaults")
                .containsExactlyInAnyOrder(
                        tuple("카테고리 1", CategoryType.INCOME, false),
                        tuple("카테고리 2",  CategoryType.OUTCOME, true),
                        tuple("카테고리 3", CategoryType.INCOME, true)
                );
    }

    @DisplayName("카테고리 항목을 조회할 때 수입 타입으로 조회할 수 있다.")
    @Test
    void 카테고리_수입_조회() {
        // given
        Category category1 = createCategory("카테고리 1", CategoryType.INCOME, false);
        Category category2 = createCategory("카테고리 2", CategoryType.OUTCOME, true);
        Category category3 = createCategory("카테고리 3", CategoryType.INCOME, true);

        categoryRepository.saveAll(List.of(category1, category2, category3));
        // when
        List<Category> categories = categoryRepository.findByType(CategoryType.INCOME);
        
        // then
        assertThat(categories)
                .extracting("name", "type", "defaults")
                .contains(
                        tuple("카테고리 1",  CategoryType.INCOME, false),
                        tuple("카테고리 3",  CategoryType.INCOME, true)
                );
    }
    @DisplayName("카테고리 항목을 조회할 때 지출 타입으로 조회할 수 있다.")
    @Test
    void 카테고리_지출_조회() {
        // given
        Category category1 = createCategory("카테고리 1", CategoryType.INCOME, false);
        Category category2 = createCategory("카테고리 2", CategoryType.OUTCOME, true);
        Category category3 = createCategory("카테고리 3", CategoryType.INCOME, true);

        categoryRepository.saveAll(List.of(category1, category2, category3));
        // when
        List<Category> categories = categoryRepository.findByType(CategoryType.OUTCOME);

        // then
        assertThat(categories)
                .extracting("name", "type", "defaults")
                .contains(
                        tuple("카테고리 2",  CategoryType.OUTCOME, true)
                );
    }


    private static Category createCategory(String name, CategoryType type, boolean defaults) {
        return Category.builder()
                .name(name)
                .type(type)
                .defaults(defaults)
                .build();
    }
}