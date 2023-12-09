package com.side.tiggle.domain.reaction.dto.req;

import com.side.tiggle.domain.member.model.Member;
import com.side.tiggle.domain.reaction.dto.ReactionDto;
import com.side.tiggle.domain.reaction.model.Reaction;
import com.side.tiggle.domain.reaction.model.ReactionType;
import com.side.tiggle.domain.transaction.model.Transaction;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReactionCreateDto {

    private ReactionType type;
}
