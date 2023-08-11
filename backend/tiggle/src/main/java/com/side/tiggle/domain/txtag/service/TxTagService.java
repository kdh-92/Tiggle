package com.side.tiggle.domain.txtag.service;

import com.side.tiggle.domain.txtag.model.TxTag;
import com.side.tiggle.domain.txtag.repository.TxTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TxTagService {

    private final TxTagRepository txTagRepository;

    public TxTag createTxTag(TxTag txTag) {
        return txTagRepository.save(txTag);
    }

    @Transactional
    public TxTag updateTxTag(Long txId, String tagNames) {
        TxTag txTag = txTagRepository.findByTxId(txId)
                .orElseThrow(() -> new IllegalArgumentException("해당 태그가 존재하지 않습니다."));

        txTag.setTagNames(tagNames);

        return txTagRepository.save(txTag);
    }
}

