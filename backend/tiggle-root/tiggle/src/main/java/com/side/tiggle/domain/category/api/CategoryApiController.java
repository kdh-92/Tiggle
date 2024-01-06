package com.side.tiggle.domain.category.api;

import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.dto.controller.CategoryUpdateReqDto;
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto;
import com.side.tiggle.domain.category.model.CategoryType;
import com.side.tiggle.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryApiController {

    private final CategoryService categoryService;
    
    @PostMapping
    public ResponseEntity<CategoryRespDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(CategoryRespDto.fromEntity(categoryService.createCategory(categoryDto)), HttpStatus.OK);
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRespDto> getCategory(@PathVariable("id") Long categoryId) {
        return new ResponseEntity<>(CategoryRespDto.fromEntity(categoryService.getCategory(categoryId)), HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CategoryRespDto>> getCategory(@PathVariable("type") CategoryType type) {
        return new ResponseEntity<>(categoryService.getCategoryType(type).stream()
                .map(CategoryRespDto::fromEntity)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryRespDto>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory()
                .stream()
                .map(CategoryRespDto::fromEntity)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoryRespDto> updateCategory(
            @PathVariable("id") Long categoryId,
            @RequestBody CategoryUpdateReqDto dto
    ) {
        return new ResponseEntity<>(CategoryRespDto.fromEntity(categoryService.updateCategory(categoryId, dto)), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable("id") Long categoryId
    ) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
