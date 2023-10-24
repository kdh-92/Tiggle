package com.side.tiggle.domain.comment.dto.resp;

import com.side.tiggle.domain.comment.service.CommentService;
import com.side.tiggle.domain.comment.dto.CommentDto;
import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.member.dto.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class CommentRespDto extends CommentDto {

    Long id;
    LocalDateTime createdAt;
    int childCount = 0;
    MemberDto sender; // 작성자 정보
    MemberDto receiver; // 수신자 정보

    public static CommentRespDto fromEntity(Comment comment) {
        CommentRespDto dto = new CommentRespDto();
        dto.setContent(comment.getContent());
        dto.setParentId(comment.getParentId());
        dto.setSender(MemberDto.fromEntity(comment.getSender()));
        dto.setReceiver(MemberDto.fromEntity(comment.getReceiver()));
        dto.setTxId(comment.getTx().getId());
        dto.setId(comment.getId());
        dto.setCreatedAt(comment.getCreatedAt());
        return dto;
    }

    public static Page<CommentRespDto> fromEntityPage(Page<Comment> comments, CommentService service){
        List<CommentRespDto> dtoList = fromEntityList(comments.getContent(), service);
        return new PageImpl<>(dtoList, comments.getPageable(), comments.getTotalElements());
    }

    public static List<CommentRespDto> fromEntityList(List<Comment> comments, CommentService commentService) {
        List<CommentRespDto> dtoList = comments.stream().map(CommentRespDto::fromEntity).collect(Collectors.toList());
        dtoList.forEach(r -> r.setChildCount(commentService));
        return dtoList;
    }

    public void setChildCount(CommentService service) {
        this.childCount = service.getChildCount(this.getTxId(), this.id);
    }
}
