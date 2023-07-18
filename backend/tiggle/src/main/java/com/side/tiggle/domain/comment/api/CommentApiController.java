package com.side.tiggle.domain.comment.api;

import com.side.tiggle.domain.comment.dto.CommentDto;
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto;
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.member.MemberDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Comment API")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentRespDto> createComment(
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody @Valid CommentDto commentDto) {
        commentDto.setSenderId(memberDto.getId());
        CommentRespDto respDto = CommentRespDto.fromEntity(commentService.createComment(commentDto));
        return new ResponseEntity<>(respDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @AuthenticationPrincipal MemberDto memberDto,
            @PathVariable("id") Long commentId,
            @RequestBody CommentUpdateReqDto dto) {
        CommentRespDto respDto = CommentRespDto.fromEntity(commentService.updateComment(memberDto.getId(), commentId, dto.getContent()));
        return new ResponseEntity<>(respDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal MemberDto memberDto,
            @PathVariable("id") Long commentId){
        commentService.deleteComment(memberDto.getId(), commentId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
