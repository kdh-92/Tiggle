package com.side.tiggle.domain.reaction

import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.reaction.dto.ReactionCreateDto
import com.side.tiggle.domain.reaction.dto.ReactionDto
import com.side.tiggle.domain.reaction.dto.ReactionSummaryDto
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.global.common.constants.HttpHeaders
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
    ): ResponseEntity<ReactionDto> {
        val reaction = reactionService.getReaction(txId, senderId) ?: return ResponseEntity.noContent().build()
        return ResponseEntity(ReactionDto.fromEntity(reaction), HttpStatus.OK)
    }

    @Operation(description = "해당 tx의 전체 reaction과 comment의 수를 조회")
    @GetMapping("/summary")
    fun getReactionSummary(
        @PathVariable(name = "id") txId: Long
    ): ResponseEntity<ReactionSummaryDto> {
        return ResponseEntity<ReactionSummaryDto>(
            ReactionSummaryDto(
                upCount = reactionService.getReactionCount(txId, ReactionType.UP),
                downCount = reactionService.getReactionCount(txId, ReactionType.DOWN),
                commentCount = commentService.getParentCount(txId)
            ), HttpStatus.OK)
    }

    @Operation(description = "리액션을 추가하거나 수정함", security = [SecurityRequirement(name = "bearer-key")])
    @PostMapping
    fun upsertReaction(
        @Parameter(hidden = true) @RequestHeader(name = HttpHeaders.MEMBER_ID) senderId: Long,
        @PathVariable(name = "id") txId: Long,
        reactionDto: ReactionCreateDto
    ): ResponseEntity<ReactionDto?> {
        val reaction = reactionService.upsertReaction(txId, senderId, reactionDto)
        return ResponseEntity(ReactionDto.fromEntity(reaction), HttpStatus.CREATED)
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