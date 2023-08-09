package com.side.tiggle.domain.txtag.service;

import com.side.tiggle.domain.txtag.TxTagDto;
import com.side.tiggle.domain.txtag.model.TxTag;
import com.side.tiggle.domain.txtag.repository.TxTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TxTagService {

    private final TxTagRepository txTagRepository;

    public TxTag createTxTag(TxTagDto dto) {
        return txTagRepository.save(dto.toEntity(dto));
    }

    public TxTag getTxTag(Long txTagId) {
        return txTagRepository.findById(txTagId)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public TxTag getListTxTag(Long txId) {
        return txTagRepository.findByTxId(txId)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public List<TxTagDto> getAllTxTag() {
        List<TxTagDto> txTagDtoList = new ArrayList<>();
        for (TxTag txTag : txTagRepository.findAll()) {
            txTagDtoList.add(TxTagDto.fromEntity(txTag));
        }

        return txTagDtoList;
    }

//    public TxTag updateTxTag(Long txId, TxTagDto dto) {
//        TxTag txTag = txTagRepository.findById(txTagId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));
//
//        txTag.setTagNames(txTagDto.getTagNames());
//
//        return txTagDto.fromEntity(txTagRepository.save(txTag));
//    }
}

