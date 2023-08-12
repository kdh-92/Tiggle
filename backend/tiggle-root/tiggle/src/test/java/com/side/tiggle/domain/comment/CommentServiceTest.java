package com.side.tiggle.domain.comment;

import com.side.tiggle.domain.comment.dto.req.CommentCreateReqDto;
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.repository.CommentRepository;
import com.side.tiggle.domain.comment.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootTest
@Transactional
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("사용자는 코멘트 작성에 성공한다")
    void 코멘트_작성() {
        CommentCreateReqDto dto = new CommentCreateReqDto();
        dto.setSenderId(1L);
        dto.setReceiverId(2L);
        dto.setTxId(1L);
        dto.setContent("Comment");
        Long id = commentService.createComment(dto).getId();

        Optional<Comment> comment = commentRepository.findById(id);
        Assertions.assertFalse(comment.isEmpty());
        Assertions.assertEquals(comment.get().getContent(), dto.getContent());
    }

    @Test
    @Disabled
    @DisplayName("유효하지 않은 tx에 코멘트를 작성할 수 없다")
    void 코멘트_작성_실패1() {
        CommentCreateReqDto dto = new CommentCreateReqDto();
        dto.setSenderId(1L);
        dto.setReceiverId(2L);
        dto.setTxId(0L);
        dto.setContent("Comment");

        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            commentService.createComment(dto);
        });
    }

    @Test
    @DisplayName("유효하지 않은 parentId로 코멘트를 작성할 수 없다")
    void 코멘트_작성_실패2() {
        CommentCreateReqDto dto = new CommentCreateReqDto();
        dto.setSenderId(1L);
        dto.setReceiverId(2L);
        dto.setTxId(1L);
        dto.setParentId(0L);
        dto.setContent("Comment");

        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            commentService.createComment(dto);
        });
    }

    @Test
    @DisplayName("유효하지 않은 receiverId로 코멘트를 작성할 수 없다")
    void 코멘트_작성_실패3() {
        CommentCreateReqDto dto = new CommentCreateReqDto();
        dto.setSenderId(1L);
        dto.setReceiverId(0L);
        dto.setTxId(1L);
        dto.setContent("Comment");

        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            commentService.createComment(dto);
        });
    }

    @Test
    @DisplayName("사용자는 코멘트 수정에 성공한다")
    void 코멘트_수정(){
        CommentUpdateReqDto dto = new CommentUpdateReqDto();
        dto.setContent("Updated comment");

        Long commentId = 1L;
        Long senderId = 3L;

        commentService.updateComment(senderId, commentId, dto.getContent());

        Optional<Comment> comment = commentRepository.findById(commentId);
        Assertions.assertFalse(comment.isEmpty());
        Assertions.assertEquals(comment.get().getContent(), dto.getContent());
    }

    @Test
    @DisplayName("사용자는 자신의 것이 아닌 코멘트를 수정할 수 없다")
    void 코멘트_수정_실패1() {
        CommentUpdateReqDto dto = new CommentUpdateReqDto();
        dto.setContent("Updated comment");

        Long commentId = 1L;
        Long senderId = 1L;
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            commentService.updateComment(senderId, commentId, dto.getContent());
        });
    }

    @Test
    @DisplayName("사용자는 자신의 것이 아닌 코멘트를 삭제할 수 없다")
    void 코멘트_삭제_실패1() {
        CommentUpdateReqDto dto = new CommentUpdateReqDto();
        dto.setContent("Updated comment");

        Long commentId = 1L;
        Long senderId = 1L;
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            commentService.deleteComment(senderId, commentId);
        });
    }

    @Test
    @DisplayName("대댓글 생성과 조회를 성공한다")
    void 대댓글_생성과_조회_성공() {
        CommentCreateReqDto dto = new CommentCreateReqDto();
        dto.setContent("대댓글 추가");
        dto.setParentId(1L);
        dto.setSenderId(1L);
        dto.setReceiverId(2L);
        dto.setTxId(1L);

        commentService.createComment(dto);

        Page<Comment> comment = commentService.getChildrenByParentId(dto.getParentId(), 0, Integer.MAX_VALUE );
        Assertions.assertFalse(comment.isEmpty());
        Assertions.assertFalse(comment.getContent().isEmpty());
    }

    @Test
    @DisplayName("트랜잭션의 댓글을 조회한다")
    void 트랜잭션_댓글_조회_성공(){
        Long txId = 1L;
        CommentCreateReqDto dto = new CommentCreateReqDto();
        dto.setContent("댓글 추가");
        dto.setSenderId(1L);
        dto.setReceiverId(2L);
        dto.setTxId(txId);

        CommentCreateReqDto dto1 = new CommentCreateReqDto();
        dto1.setContent("대댓글 추가");
        dto1.setParentId(1L);
        dto1.setSenderId(1L);
        dto1.setReceiverId(2L);
        dto1.setTxId(txId);

        commentService.createComment(dto);
        commentService.createComment(dto1);

        Page<Comment> comments = commentService.getParentsByTxId(txId, 0, Integer.MAX_VALUE );

        // 댓글을 반환한다
        Optional<Comment> shouldReturn = comments.getContent().stream().filter(it ->
                it.getContent().equals(dto.getContent())).findFirst();
        Assertions.assertTrue(shouldReturn.isPresent());

        // 대댓글은 반환하지 않는다
        Optional<Comment> shouldNotReturn = comments.getContent().stream().filter(it ->
                it.getContent().equals(dto1.getContent())).findFirst();
        Assertions.assertTrue(shouldNotReturn.isEmpty());
    }
    
}

