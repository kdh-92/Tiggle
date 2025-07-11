package com.side.tiggle.domain.comment.api

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto
import com.side.tiggle.domain.comment.dto.resp.CommentPageRespDto
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@Tag(name = "Comment API")
@RestController
@RequestMapping("/api/v1/comments")
class CommentApiController(
    private val commentService: CommentService,
    private val transactionService: TransactionService
) {
    @Operation(summary = "대댓글 조회 API", description = "댓글의 id를 가지고 대댓글을 조회한다")
    @GetMapping("/{id}/replies")
    fun getAllCommentsByCommentId(
        @PathVariable @Min(1) id: Long,
        @RequestParam(name = "index", defaultValue = "0") @Min(0) page: Int,
        @RequestParam(name = "pageSize", defaultValue = "5") @Min(1) @Max(100) size: Int
    ): ResponseEntity<ApiResponse<CommentPageRespDto>> {
        val pagedComments = commentService.getChildrenByParentId(id, page, size)
        return ResponseEntity
            .ok(ApiResponse.success(pagedComments))
    }

    @PostMapping
    @Operation(summary = "코멘트 작성", security = [SecurityRequirement(name = "bearer-key")])
    fun createComment(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @RequestBody @Valid commentDto: CommentCreateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        val tx = transactionService.getTransactionOrThrow(commentDto.txId)
        commentService.createComment(memberId, tx, commentDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(null, message = "댓글이 생성되었습니다."))
    }

    @PutMapping("/{id}")
    @Operation(summary = "코멘트 수정", security = [SecurityRequirement(name = "bearer-key")])
    fun updateComment(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) commentId: Long,
        @RequestBody @Valid dto: CommentUpdateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        commentService.updateComment(memberId, commentId, dto)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "댓글이 수정되었습니다."))
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "코멘트 삭제", security = [SecurityRequirement(name = "bearer-key")])
    fun deleteComment(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) memberId: Long,
        @PathVariable("id") @Min(1) commentId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        commentService.deleteComment(memberId, commentId)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "댓글이 삭제되었습니다."))
    }

    @GetMapping("/{id}/comments")
    fun getAllCommentsByTx(
        @PathVariable @Min(1) id: Long,
        @RequestParam(name = "index", defaultValue = DEFAULT_INDEX) @Min(0) index: Int,
        @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) @Min(1) @Max(100) pageSize: Int
    ): ResponseEntity<ApiResponse<CommentPageRespDto>> {
        val pagedResult = commentService.getParentsByTxId(id, index, pageSize)
        return ResponseEntity
            .ok(ApiResponse.success(pagedResult))
    }

    companion object {
        private const val DEFAULT_INDEX = "0"
        private const val DEFAULT_PAGE_SIZE = "5"
    }
}
