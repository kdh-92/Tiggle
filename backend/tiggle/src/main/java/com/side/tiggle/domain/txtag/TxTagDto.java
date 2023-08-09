package com.side.tiggle.domain.txtag;

import com.side.tiggle.domain.txtag.model.TxTag;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TxTagDto {
    long id;
    long txId;
    long memberId;
    String tagNames;

    public static TxTagDto fromEntity(TxTag txTag) {
        return TxTagDto.builder()
                .id(txTag.getId())
                .txId(txTag.getTxId())
                .memberId(txTag.getMemberId())
                .tagNames(txTag.getTagNames())
                .build();
    }

    public TxTag toEntity(TxTagDto dto) {
        return TxTag.builder()
                .txId(dto.getTxId())
                .memberId(dto.getMemberId())
                .tagNames(dto.getTagNames())
                .build();
    }
}
