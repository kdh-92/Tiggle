package com.side.tiggle.domain.reaction.dto;

import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ReactionDto {
    private Long txId;
    private Long senderId;
    private Long receiverId;
    private ReactionType type;

    public static ReactionDto fromEntity(Reaction reaction) {
        return ReactionDto.builder()
                .txId(reaction.getTxId())
                .receiverId(reaction.getReceiverId())
                .senderId(reaction.getSenderId())
                .type(reaction.getType())
                .build();
    }
}
