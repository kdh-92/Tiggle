package com.side.tiggle.domain.transaction.dto;

import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.model.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {

    @Getter
    @Setter
    public static class TransactionRequestDto {

        @NotBlank(message = "member 입력이 필요합니다.")
        private Long memberId;
        private Long parentId;
        private TransactionType type;
        @NotBlank(message = "이미지 입력이 필요합니다.")
        private String imageUrl;
        private int amount;
        private LocalDate date;
        private String content;
        private String reason;
    }

    @Getter
    @AllArgsConstructor
    public static class TransactionResponseDto {

        private Long id;
        private Long memberId;
        private Long parentId;
    }

    @Getter
    @Setter
    public static class TransactionUpdateRequestDto {

        private Long parentId;
        private TransactionType type;
        private String imageUrl;
        private int amount;
        private LocalDate date;
        private String content;
        private String reason;
    }

    public static Transaction toEntity(TransactionRequestDto dto) {
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
    public static TransactionResponseDto fromEntity(Transaction transaction) {
        return new TransactionResponseDto(transaction.getId(), transaction.getMemberId(), transaction.getParentId());
    }
}
