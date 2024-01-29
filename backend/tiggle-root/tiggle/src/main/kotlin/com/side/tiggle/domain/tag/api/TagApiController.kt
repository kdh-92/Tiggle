package com.side.tiggle.domain.tag.api

import com.side.tiggle.domain.tag.dto.TagDto
import com.side.tiggle.domain.tag.service.TagService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/tag")
class TagApiController(
    private val tagService: TagService
) {

    @PostMapping
    fun createTag(
        @RequestBody tagDto: TagDto
    ): ResponseEntity<TagDto> {
        return ResponseEntity(tagService.createTag(tagDto), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTag(
        @PathVariable("id") tagId: Long
    ): ResponseEntity<TagDto> {
        return ResponseEntity(tagService.getTag(tagId), HttpStatus.OK)
    }

    @GetMapping("/all")
    fun getAllDefaultTag(): ResponseEntity<List<TagDto>> {
        return ResponseEntity(
            tagService.getAllDefaultTag().map { TagDto.fromEntity(it) }, HttpStatus.OK
        )
    }

    @PutMapping("/{id}")
    fun updateTag(
        @PathVariable("id") tagId: Long,
        @RequestBody tagDto: TagDto
    ): ResponseEntity<TagDto> {
        return ResponseEntity(tagService.updateTag(tagId, tagDto), HttpStatus.OK)
    }
}