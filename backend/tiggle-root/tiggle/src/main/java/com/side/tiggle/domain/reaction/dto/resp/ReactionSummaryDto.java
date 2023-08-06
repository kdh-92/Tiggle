package com.side.tiggle.domain.reaction.dto.resp;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReactionSummaryDto {
    long upCount;
    long downCount;
    long commentCount;

    @Builder
    ReactionSummaryDto(long upCount, long downCount, long commentCount) {
        this.upCount = upCount;
        this.downCount = downCount;
        this.commentCount = commentCount;
    }
}
