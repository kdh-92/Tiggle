package com.side.tiggle.domain.txtag.service;

import com.side.tiggle.domain.txtag.TxTagDto;
import com.side.tiggle.domain.txtag.model.TxTag;
import com.side.tiggle.domain.txtag.repository.TxTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TxTagService {

    private final TxTagRepository txTagRepository;

    public TxTagDto createTxTag(TxTagDto txTagDto) {
        TxTag txTag = TxTag.builder()
                .txId(txTagDto.getTxId())
                .memberId(txTagDto.getMemberId())
                .tagName(txTagDto.getTagName())
                .build();

        return txTagDto.fromEntity(txTagRepository.save(txTag));
    }

    public TxTagDto getTxTag(Long txTagId) {
        return TxTagDto.fromEntity(txTagRepository.findById(txTagId)
                .orElseThrow(() -> new RuntimeException("")));
    }

    public List<TxTagDto> getAllTxTag() {
        List<TxTagDto> txTagDtoList = new ArrayList<>();
        for (TxTag txTag : txTagRepository.findAll()) {
            txTagDtoList.add(TxTagDto.fromEntity(txTag));
        }

        return txTagDtoList;
    }

    public TxTagDto updateTxTag(Long txTagId, TxTagDto txTagDto) {
        TxTag txTag = txTagRepository.findById(txTagId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        txTag.setTagName(txTagDto.getTagName());

        return txTagDto.fromEntity(txTagRepository.save(txTag));
    }

    // delete
}

