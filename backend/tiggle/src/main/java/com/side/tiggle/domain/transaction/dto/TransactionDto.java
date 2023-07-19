package com.side.tiggle.domain.transaction.dto;

import com.side.tiggle.domain.transaction.model.Transaction;
import com.side.tiggle.domain.transaction.model.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TransactionDto {

    Long id;
    Long memberId;
    Long parentId;
    TransactionType type;
    String imageUrl;
    Integer amount;
    LocalDate date;
    String content;
    String reason;

    public static TransactionDto fromEntity(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.id = transaction.getId();
        dto.memberId = transaction.getMemberId();
        dto.parentId = transaction.getParentId();
        dto.type = transaction.getType();
        dto.imageUrl = transaction.getImageUrl();
        dto.amount = transaction.getAmount();
        dto.date = transaction.getDate();
        dto.content = transaction.getContent();
        dto.reason = transaction.getReason();

        return dto;
    }
}
