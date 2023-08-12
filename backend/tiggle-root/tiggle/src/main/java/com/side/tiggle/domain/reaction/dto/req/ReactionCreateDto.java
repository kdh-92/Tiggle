package com.side.tiggle.domain.reaction.dto.req;

import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReactionCreateDto {
    ReactionType type;

    public static ReactionCreateDto fromEntity(Reaction reaction) {
        ReactionCreateDto dto = new ReactionCreateDto();
        dto.type = reaction.getType();

        return dto;
    }

    public Reaction toEntity(long senderId, Transaction tx) {
        return Reaction.builder()
                .receiverId(tx.getMemberId())
                .senderId(senderId)
                .txId(tx.getId())
                .type(type)
                .build();
    }
}
