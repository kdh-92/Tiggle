package com.side.tiggle.domain.comment.api;

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto;
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto;
import com.side.tiggle.domain.comment.dto.resp.CommentRespDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.member.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import com.side.tiggle.global.common.constants.HttpHeaders;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Comment API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentApiController {

    private final CommentService commentService;

    @Operation(summary = "대댓글 조회 API", description = "댓글의 id를 가지고 대댓글을 조회한다")
    @GetMapping("/{id}/replies")
    public ResponseEntity<Page<CommentRespDto>> getAllCommentsByCommentId(
            @PathVariable Long id,
            @RequestParam(name = "index", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "5") int size
    ){
        Page<Comment> pagedComments = commentService.getChildrenByParentId(id, page, size);
        Page<CommentRespDto> pagedResult = CommentRespDto.fromEntityPage(pagedComments);
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentRespDto> createComment(
            @RequestHeader(name = HttpHeaders.MEMBER_ID) long memberId,
            @RequestBody @Valid CommentCreateReqDto commentDto) {
        commentDto.setSenderId(memberDto.getId());
        CommentRespDto respDto = CommentRespDto.fromEntity(commentService.createComment(commentDto));
        return new ResponseEntity<>(respDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentRespDto> updateComment(
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
