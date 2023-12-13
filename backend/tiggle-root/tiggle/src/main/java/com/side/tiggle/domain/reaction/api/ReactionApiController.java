package com.side.tiggle.domain.reaction.api;

import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.reaction.dto.ReactionDto;
import com.side.tiggle.domain.reaction.dto.req.ReactionCreateDto;
import com.side.tiggle.domain.reaction.dto.resp.ReactionSummaryDto;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import com.side.tiggle.domain.reaction.service.ReactionService;
import com.side.tiggle.global.common.constants.HttpHeaders;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Reaction API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transaction/{id}/reaction")
public class ReactionApiController {

    private final ReactionService reactionService;
    private final CommentService commentService;

    @Operation(description = "해당 tx에 대한 나의 reaction을 조회")
    @GetMapping
    public ResponseEntity<ReactionDto> getReaction(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long senderId,
            @PathVariable(name = "id") long txId
    ){
        Reaction reaction = reactionService.getReaction(txId, senderId);
        if (reaction == null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(
                ReactionDto.fromEntity(reaction), HttpStatus.OK
        );
    }

    @Operation(description = "해당 tx의 전체 reaction과 comment의 수를 조회")
    @GetMapping("/summary")
    public ResponseEntity<ReactionSummaryDto> getReactionSummary(
            @PathVariable(name = "id") long txId
    ){
        ReactionSummaryDto dto = ReactionSummaryDto.builder()
                .downCount(reactionService.getReactionCount(txId, ReactionType.DOWN))
                .upCount(reactionService.getReactionCount(txId, ReactionType.UP))
                .commentCount(commentService.getParentCount(txId))
                .build();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(description = "리액션을 추가하거나 수정함")
    @PostMapping
    public ResponseEntity<ReactionDto> upsertReaction(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long senderId,
            @PathVariable(name = "id") long txId,
            ReactionCreateDto reactionDto
    ) {
        Reaction reaction = reactionService.upsertReaction(txId, senderId, reactionDto);
        return new ResponseEntity<>(ReactionDto.fromEntity(reaction), HttpStatus.CREATED);
    }

    @Operation(description = "Reaction을 제거")
    @DeleteMapping
    public ResponseEntity<Long> deleteReaction(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long senderId,
            @PathVariable(name = "id") long txId
    ) {
        this.reactionService.deleteReaction(txId, senderId);
        return ResponseEntity.noContent().build();
    }
}
