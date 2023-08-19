package com.side.tiggle.domain.txtag.service;

import com.side.tiggle.domain.txtag.model.TxTag;
import com.side.tiggle.domain.txtag.repository.TxTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TxTagService {

    private final TxTagRepository txTagRepository;

    public TxTag createTxTag(TxTag txTag) {
        return txTagRepository.save(txTag);
    }

    @Transactional
    public TxTag updateTxTag(Long txId, Long memberId, String tagNames) {
        TxTag txTag = txTagRepository.findByTxId(txId)
                .orElse(new TxTag(txId, memberId, tagNames));
        txTag.setTagNames(tagNames);

        return txTagRepository.save(txTag);
    }
}

