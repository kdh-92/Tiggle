package com.side.tiggle.domain.reaction.dto;

import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@AllArgsConstructor
public class ReactionDto {
    private Long txId;
    private Long senderId;
    private Long receiverId;
    private ReactionType type;

    public Reaction toEntity(Transaction tx, Member sender, Member receiver) {
        return Reaction.builder()
                .tx(tx)
                .sender(sender)
                .receiver(receiver)
                .type(type)
                .build();
    }

    public static ReactionDto fromEntity(Reaction reaction) {
        return ReactionDto.builder()
                .txId(reaction.getTx().getId())
                .senderId(reaction.getSender().getId())
                .receiverId(reaction.getReceiver().getId())
                .type(reaction.getType())
                .build();
    }
}
