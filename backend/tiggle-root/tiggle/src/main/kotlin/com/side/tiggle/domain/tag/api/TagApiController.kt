package com.side.tiggle.domain.tag.api

import com.side.tiggle.domain.tag.dto.req.TagCreateReqDto
import com.side.tiggle.domain.tag.dto.req.TagUpdateReqDto
import com.side.tiggle.domain.tag.dto.resp.TagRespDto
import com.side.tiggle.domain.tag.service.TagService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
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
    @Operation(summary = "태그 생성", security = [SecurityRequirement(name = "bearer-key")])
    fun createTag(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestBody @Valid createReqDto: TagCreateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        tagService.createTag(createReqDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(null, message = "태그가 생성되었습니다."))
    }

    @GetMapping("/{id}")
    fun getTag(
        @PathVariable("id") @Min(1) tagId: Long
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
    @Operation(summary = "태그 수정", security = [SecurityRequirement(name = "bearer-key")])
    fun updateTag(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) tagId: Long,
        @RequestBody @Valid updateReqDto: TagUpdateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        tagService.updateTag(tagId, updateReqDto)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "태그가 수정되었습니다."))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "태그 삭제", security = [SecurityRequirement(name = "bearer-key")])
    fun deleteTag(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) tagId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        tagService.deleteTag(tagId)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "태그가 삭제되었습니다."))
    }
}
