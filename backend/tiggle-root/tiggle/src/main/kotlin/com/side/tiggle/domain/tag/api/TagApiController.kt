package com.side.tiggle.domain.tag.api

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.service.TagService
import com.side.tiggle.global.common.ApiResponse
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
        @RequestBody createReqDto: TagCreateReqDto
    ): ResponseEntity<ApiResponse<TagRespDto>> {
        val tag = tagService.createTag(createReqDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(tag))
    }

    @GetMapping("/{id}")
    fun getTag(
        @PathVariable("id") tagId: Long
    ): ResponseEntity<ApiResponse<TagRespDto>> {
        val tag = tagService.getTag(tagId)
        return ResponseEntity
            .ok(ApiResponse.success(tag))
    }

    @GetMapping("/all")
    fun getAllDefaultTag(): ResponseEntity<ApiResponse<List<TagRespDto>>> {
        val getTags = tagService.getAllDefaultTag()
        return ResponseEntity
            .ok(ApiResponse.success(getTags))
    }

    @PutMapping("/{id}")
    fun updateTag(
        @PathVariable("id") tagId: Long,
        @RequestBody updateReqDto: TagUpdateReqDto
    ): ResponseEntity<ApiResponse<TagRespDto>> {
        val tag = tagService.updateTag(tagId, updateReqDto)
        return ResponseEntity
            .ok(ApiResponse.success(tag))
    }
}
