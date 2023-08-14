package com.side.tiggle.domain.txtag.dto;

import com.side.tiggle.domain.txtag.model.TxTag;
import lombok.*;

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

    public TxTag toEntity(TxTagDto dto) {
        return TxTag.builder()
                .txId(dto.getTxId())
                .memberId(dto.getMemberId())
                .tagNames(dto.getTagNames())
                .build();
    }

    public static TxTagDto fromEntity(TxTag txTag) {
        return TxTagDto.builder()
                .id(txTag.getId())
                .txId(txTag.getTxId())
                .memberId(txTag.getMemberId())
                .tagNames(txTag.getTagNames())
                .build();
    }
}
