package com.side.tiggle.domain.reaction.api

import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryRespDto
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.domain.transaction.service.TransactionService
import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Min
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@Tag(name = "Reaction API")
@RestController
@RequestMapping("/api/v1/transaction/{id}/reaction")
class ReactionApiController(
    private val reactionService: ReactionService,
    private val transactionService: TransactionService
) {

    //TODO out Any? 필요한지 체크 후 반환 타입 수정
    @Operation(description = "해당 tx에 대한 나의 reaction을 조회", security = [SecurityRequirement(name = "bearer-key")])
    @GetMapping
    fun getReaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") @Min(1) txId: Long
//    ): ResponseEntity<ReactionRespDto> {
    ): ResponseEntity<out Any?> {
        val reaction = reactionService.getReaction(txId, senderId) ?: return ResponseEntity.noContent().build()
        return ResponseEntity
            .ok(ApiResponse.success(reaction))
    }

    @Operation(description = "해당 tx의 전체 reaction과 comment의 수를 조회")
    @GetMapping("/summary")
    fun getReactionSummary(
        @PathVariable(name = "id") @Min(1) txId: Long
    ): ResponseEntity<ApiResponse<ReactionSummaryRespDto>> {
        val reactionSummary = reactionService.getReactionSummaryDto(txId)
        return ResponseEntity
            .ok(ApiResponse.success(reactionSummary, message = "반응 요약 조회 성공"))
    }

    @Operation(description = "리액션을 추가하거나 수정함", security = [SecurityRequirement(name = "bearer-key")])
    @PostMapping
    fun upsertReaction(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") @Min(1) txId: Long,
        @RequestBody @Valid createReqDto: ReactionCreateReqDto
    ): ResponseEntity<ApiResponse<Nothing>> {
        val tx = transactionService.getTransactionOrThrow(txId)
        reactionService.upsertReaction(txId, senderId, tx.memberId, createReqDto)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success(null, message = "반응이 생성/수정되었습니다."))
    }

    @Operation(description = "Reaction을 제거", security = [SecurityRequirement(name = "bearer-key")])
    @DeleteMapping
    fun deleteReaction(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") @Min(1) txId: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        reactionService.deleteReaction(txId, senderId)
        return ResponseEntity
            .ok(ApiResponse.success(null, message = "반응이 삭제되었습니다."))
    }
}
