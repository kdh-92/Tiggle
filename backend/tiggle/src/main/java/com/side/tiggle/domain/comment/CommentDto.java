package com.side.tiggle.domain.comment;

import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentDto {
    @NotNull
    Long txId;
    Long parentId;
    @NotNull
    Long senderId;
    @NotNull
    Long receiverId;
    @NotNull @Size(max = 255)
    String content;

    public Comment toEntity(Transaction tx) {
        return Comment.builder()
            .tx(tx)
            .parentId(parentId)
            .senderId(senderId)
            .receiverId(receiverId)
            .content(content)
            .build();
    }

    public static CommentDto fromEntity(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.txId = comment.getTx().getId();
        dto.parentId = comment.getParentId();
        dto.senderId = comment.getSenderId();
        dto.receiverId = comment.getReceiverId();
        dto.content = comment.getContent();

        return dto;
    }

    public static class Request {

        @Getter
        @Setter
        public static class Update {
            String content;
        }
    }

    public static class Response {

        @Getter
        @Setter
        @NoArgsConstructor
        public static class CommentRespDto {

            Long id;
            LocalDateTime createdAt;
            @NotNull
            Long txId;
            Long parentId;
            @NotNull
            Long senderId;
            @NotNull
            Long receiverId;
            @NotNull @Size(max = 255)
            String content;

            public static CommentRespDto fromEntity(Comment comment) {
                CommentRespDto dto = new CommentRespDto();
                dto.setContent(comment.getContent());
                dto.setParentId(comment.getParentId());
                dto.setReceiverId(comment.getReceiverId());
                dto.setSenderId(comment.getSenderId());
                dto.setTxId(comment.getTx().getId());
                dto.setId(comment.getId());
                dto.setCreatedAt(comment.getCreatedAt());
                return dto;
            }

            public static Page<CommentRespDto> fromEntityPage(Page<Comment> commentPage){
                List<CommentRespDto> commentRespDtos = commentPage.getContent()
                        .stream().map(CommentRespDto::fromEntity).collect(Collectors.toList());
                return new PageImpl<>(commentRespDtos, commentPage.getPageable(), commentPage.getTotalElements());
            }
        }
    }
}
