package com.side.tiggle.domain.reaction;

import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReactionDto {
    long id;
    long txId;
    long senderId;
    long receiverId;
    ReactionType type;

    public static ReactionDto fromEntity(Reaction reaction) {
        ReactionDto dto = new ReactionDto();
        dto.id = reaction.getId();
        dto.txId = reaction.getTxId();
        dto.senderId = reaction.getSenderId();
        dto.receiverId = reaction.getReceiverId();
        dto.type = reaction.getType();

        return dto;
    }
}
