package com.side.tiggle.domain.comment;

import com.side.tiggle.domain.comment.dto.CommentDto;
import com.side.tiggle.domain.comment.dto.req.CommentUpdateReqDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.comment.repository.CommentRepository;
import com.side.tiggle.domain.comment.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        CommentDto dto = new CommentDto();
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
    @DisplayName("유효하지 않은 tx에 코멘트를 작성할 수 없다")
    void 코멘트_작성_실패1() {
        CommentDto dto = new CommentDto();
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
        CommentDto dto = new CommentDto();
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
        CommentDto dto = new CommentDto();
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
    @DisplayName("사용자는 코멘트 삭제에 성공한다, 삭제는 Soft Delete 되어야 한다")
    void 코멘트_삭제(){
        CommentUpdateReqDto dto = new CommentUpdateReqDto();
        dto.setContent("Updated comment");

        Long commentId = 1L;
        Long senderId = 3L;
        commentService.deleteComment(senderId, commentId);

        Optional<Comment> comment = commentRepository.findById(commentId);
        Assertions.assertFalse(comment.isEmpty());
        Assertions.assertTrue(comment.get().isDeleted());
        Assertions.assertNotNull(comment.get().getDeletedAt());
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
}

