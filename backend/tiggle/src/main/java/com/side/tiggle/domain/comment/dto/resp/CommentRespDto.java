package com.side.tiggle.domain.comment.dto.resp;

import com.side.tiggle.domain.comment.dto.CommentDto;
import com.side.tiggle.domain.comment.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentRespDto extends CommentDto {

    Long id;
    LocalDateTime createdAt;

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
}
