package com.side.tiggle.domain.txtag;

import com.side.tiggle.domain.txtag.model.TxTag;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxTagDto {
    long id;
    long txId;
    long memberId;
    String tagName;

    public static TxTagDto fromEntity(TxTag txTag) {
        TxTagDto dto = new TxTagDto();
        dto.id = txTag.getId();
        dto.txId = txTag.getTxId();
        dto.memberId = txTag.getMemberId();
        dto.tagName = txTag.getTagName();

        return dto;
    }
}
