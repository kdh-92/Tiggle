package com.side.tiggle.domain.tag.api

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.service.TagService
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/v1/tag")
class TagApiController(
    private val tagService: TagService
) {

    @PostMapping
    fun createTag(
        @RequestBody @Valid createReqDto: TagCreateReqDto
    ): ResponseEntity<TagRespDto> {
        return ResponseEntity(tagService.createTag(createReqDto), HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getTag(
        @PathVariable("id") @Min(1) tagId: Long
    ): ResponseEntity<TagRespDto> {
        return ResponseEntity(tagService.getTag(tagId), HttpStatus.OK)
    }

    @GetMapping("/all")
    fun getAllDefaultTag(): ResponseEntity<List<TagRespDto>> {
        return ResponseEntity(tagService.getAllDefaultTag(), HttpStatus.OK)
    }

    @PutMapping("/{id}")
    fun updateTag(
        @PathVariable("id") @Min(1) tagId: Long,
        @RequestBody @Valid updateReqDto: TagUpdateReqDto
    ): ResponseEntity<TagRespDto> {
        return ResponseEntity(tagService.updateTag(tagId, updateReqDto), HttpStatus.OK)
    }
}
