package com.side.tiggle.domain.category.api;

import com.side.tiggle.domain.asset.dto.AssetDto;
import com.side.tiggle.domain.asset.service.AssetService;
import com.side.tiggle.domain.category.dto.CategoryDto;
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto;
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
    @PutMapping
    public ResponseEntity<CategoryRespDto> createCategory(@RequestBody CategoryDto categoryDto) {
        return new ResponseEntity<>(CategoryRespDto.fromEntity(categoryService.createCategory(categoryDto)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRespDto> getCategory(@PathVariable("id") Long categoryId) {
        return new ResponseEntity<>(CategoryRespDto.fromEntity(categoryService.getCategory(categoryId)), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryRespDto>> getAllCategory() {
        return new ResponseEntity<>(categoryService.getAllCategory()
                .stream()
                .map(CategoryRespDto::fromEntity)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
}
