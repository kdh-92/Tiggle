package com.side.tiggle.domain.transaction.dto;

import com.side.tiggle.domain.comment.model.Comment;
import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.model.TransactionType;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    @Getter
    @Setter
    public static class Request {

        @Getter
        @Builder
        @AllArgsConstructor
        public static class ReqDto {

            @NotBlank(message = "member 입력이 필요합니다.")
            private Long id;
            private Long memberId;
            private Long parentId;
            private TransactionType type;
            @NotBlank(message = "이미지 입력이 필요합니다.")
            private String imageUrl;
            private int amount;
            private LocalDate date;
            private String content;
            private String reason;

            public static Transaction toEntity(ReqDto dto) {
                return Transaction.builder()
                        .memberId(dto.getMemberId())
                        .parentId(dto.getParentId())
                        .type(dto.getType())
                        .imageUrl(dto.getImageUrl())
                        .amount(dto.getAmount())
                        .date(dto.getDate())
                        .content(dto.getContent())
                        .reason(dto.getReason())
                        .build();
            }
        }
    }

    public static class Response {

        @Getter
        @Builder
        @AllArgsConstructor
        public static class RespDto {

            private Long id;
            private Long memberId;
            private Long parentId;
            private TransactionType type;
            private String imageUrl;
            private int amount;
            private LocalDate date;
            private String content;
            private String reason;
            private LocalDateTime createdAt;
            private List<Comment> commentList;

            public static RespDto fromEntity(Transaction tx) {
                return RespDto.builder()
                        .id(tx.getId())
                        .memberId(tx.getMemberId())
                        .parentId(tx.getParentId())
                        .type(tx.getType())
                        .imageUrl(tx.getImageUrl())
                        .amount(tx.getAmount())
                        .date(tx.getDate())
                        .content(tx.getContent())
                        .reason(tx.getReason())
                        .createdAt(tx.getCreatedAt())
                        .commentList(tx.getCommentList())
                        .build();
            }

            public static Page<RespDto> fromEntityPage(Page<Transaction> txPage) {
                return new PageImpl<>(
                        txPage.getContent()
                                .stream()
                                .map(RespDto::fromEntity)
                                .collect(Collectors.toList()),
                        txPage.getPageable(),
                        txPage.getTotalElements()
                );
            }
        }
    }
}
