package com.side.tiggle.domain.comment.api

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@Tag(name = "Comment API")
@RestController
@RequestMapping("/api/v1/comments")
class CommentApiController(
    private val commentService: CommentService
) {
    @Operation(summary = "대댓글 조회 API", description = "댓글의 id를 가지고 대댓글을 조회한다")
    @GetMapping("/{id}/replies")
    fun getAllCommentsByCommentId(
        @PathVariable id: Long?,
        @RequestParam(name = "index", defaultValue = "0") page: Int,
        @RequestParam(name = "pageSize", defaultValue = "5") size: Int
    ): ResponseEntity<Page<CommentRespDto>> {
        val pagedComments = commentService.getChildrenByParentId(id, page, size)
        val pagedResult = CommentRespDto.fromEntityPage(pagedComments, commentService)
        return ResponseEntity(pagedResult, HttpStatus.OK)
    }

    @PostMapping
    @Operation(summary = "코멘트 작성", security = [SecurityRequirement(name = "bearer-key")])
    fun createComment(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestBody commentDto: @Valid CommentCreateReqDto): ResponseEntity<CommentRespDto> {
        val respDto = CommentRespDto.fromEntity(commentService.createComment(memberId, commentDto))
        return ResponseEntity(respDto, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    @Operation(summary = "코멘트 수정", security = [SecurityRequirement(name = "bearer-key")])
    fun updateComment(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") commentId: Long,
        @RequestBody dto: CommentUpdateReqDto): ResponseEntity<CommentRespDto> {
        val respDto = CommentRespDto.fromEntity(commentService.updateComment(memberId, commentId, dto.content))
        return ResponseEntity(respDto, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "코멘트 삭제", security = [SecurityRequirement(name = "bearer-key")])
    fun deleteComment(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") commentId: Long): ResponseEntity<Any> {
        commentService.deleteComment(memberId, commentId)
        return ResponseEntity(null, HttpStatus.NO_CONTENT)
    }
}