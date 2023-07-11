package com.side.tiggle.domain.comment;
import com.side.tiggle.domain.comment.model.Comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    long id;
    long txId;
    long parentId;
    long senderId;
    long receiverId;
    String content;

    public static CommentDto fromEntity(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.id = comment.getId();
        dto.txId = comment.getTxId();
        dto.parentId = comment.getParentId();
        dto.senderId = comment.getSenderId();
        dto.receiverId = comment.getReceiverId();
        dto.content = comment.getContent();

        return dto;
    }
}
