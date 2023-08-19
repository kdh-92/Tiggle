package com.side.tiggle.domain.comment.dto;

import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

    public Comment toEntity(Transaction tx, Member sender) {
        return Comment.builder()
                .tx(tx)
                .parentId(parentId)
                .sender(sender)
                .receiverId(receiverId)
                .content(content)
                .build();
    }

    public static CommentDto fromEntity(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.txId = comment.getTx().getId();
        dto.parentId = comment.getParentId();
        dto.senderId = comment.getSender().getId();
        dto.receiverId = comment.getReceiverId();
        dto.content = comment.getContent();

        return dto;
    }
}
