package com.side.tiggle.domain.reaction.api

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateReqDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionRespDto
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryRespDto
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Reaction API")
@RestController
@RequestMapping("/api/v1/transaction/{id}/reaction")
class ReactionApiController(
    val reactionService: ReactionService,
    val commentService: CommentService
) {

    @Operation(description = "해당 tx에 대한 나의 reaction을 조회", security = [SecurityRequirement(name = "bearer-key")])
    @GetMapping
    fun getReaction(
        @Parameter(hidden = true)
        @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") txId: Long
    ): ResponseEntity<ReactionRespDto> {
        val reaction = reactionService.getReaction(txId, senderId) ?: return ResponseEntity.noContent().build()
        return ResponseEntity(reaction, HttpStatus.OK)
    }

    @Operation(description = "해당 tx의 전체 reaction과 comment의 수를 조회")
    @GetMapping("/summary")
    fun getReactionSummary(
        @PathVariable(name = "id") txId: Long
    ): ResponseEntity<ReactionSummaryRespDto> {
        return ResponseEntity<ReactionSummaryRespDto>(
            ReactionSummaryRespDto(
                upCount = reactionService.getReactionCount(txId, ReactionType.UP),
                downCount = reactionService.getReactionCount(txId, ReactionType.DOWN),
                commentCount = commentService.getParentCount(txId)
            ), HttpStatus.OK
        )
    }

    @Operation(description = "리액션을 추가하거나 수정함", security = [SecurityRequirement(name = "bearer-key")])
    @PostMapping
    fun upsertReaction(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") txId: Long,
        createReqDto: ReactionCreateReqDto
    ): ResponseEntity<ReactionRespDto> {
        val reaction = reactionService.upsertReaction(txId, senderId, createReqDto)
        return ResponseEntity(reaction, HttpStatus.CREATED)
    }

    @Operation(description = "Reaction을 제거", security = [SecurityRequirement(name = "bearer-key")])
    @DeleteMapping
    fun deleteReaction(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") txId: Long
    ): ResponseEntity<Long> {
        reactionService.deleteReaction(txId, senderId)
        return ResponseEntity.noContent().build()
    }
}