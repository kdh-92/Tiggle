package com.side.tiggle.domain.comment.api;

import com.side.tiggle.domain.comment.dto.CommentDto;
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto;
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.global.common.constants.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Comment API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentRespDto> createComment(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long memberId,
            @RequestBody @Valid CommentDto commentDto) {
        commentDto.setSenderId(memberId);
        CommentRespDto respDto = CommentRespDto.fromEntity(commentService.createComment(commentDto));
        return new ResponseEntity<>(respDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto> updateComment(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long memberId,
            @PathVariable("id") Long commentId,
            @RequestBody CommentUpdateReqDto dto) {
        CommentRespDto respDto = CommentRespDto.fromEntity(commentService.updateComment(memberId, commentId, dto.getContent()));
        return new ResponseEntity<>(respDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long memberId,
            @PathVariable("id") Long commentId){
        commentService.deleteComment(memberId, commentId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}
