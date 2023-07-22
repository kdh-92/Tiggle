package com.side.tiggle.domain.comment.api;

import com.side.tiggle.domain.comment.CommentDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.member.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Operation(summary = "대댓글 조회 API", description = "댓글의 id를 가지고 대댓글을 조회한다")
    @GetMapping("/{id}/replies")
    public ResponseEntity<Page<CommentDto.Response.CommentRespDto>> getAllCommentsByCommentId(
            @PathVariable Long id,
            @RequestParam(name = "index", defaultValue = "0") int index,
            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize
    ){
        Pageable pageable = PageRequest.of(index, pageSize, Sort.Direction.DESC, "id");
        Page<Comment> pagedComments = commentService.getChildrenByParentId(id, pageable);
        Page<CommentDto.Response.CommentRespDto> pagedResult = CommentDto.Response.CommentRespDto.fromEntityPage(pagedComments);
        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }


    public ResponseEntity<CommentDto.Response.CommentRespDto> createComment(
            @AuthenticationPrincipal MemberDto memberDto,
            @RequestBody @Valid CommentDto commentDto) {
        commentDto.setSenderId(memberDto.getId());
        CommentDto.Response.CommentRespDto respDto = CommentDto.Response.CommentRespDto.fromEntity(commentService.createComment(commentDto));
        return new ResponseEntity<>(respDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentDto.Response.CommentRespDto> updateComment(
            @AuthenticationPrincipal MemberDto memberDto,
            @PathVariable("id") Long commentId,
            @RequestBody CommentDto.Request.Update dto) {
        CommentDto.Response.CommentRespDto respDto = CommentDto.Response.CommentRespDto.fromEntity(commentService.updateComment(memberDto.getId(), commentId, dto.getContent()));
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
