package com.side.tiggle.domain.txtag.service

import com.side.tiggle.domain.txtag.model.TxTag
import com.side.tiggle.domain.txtag.repository.TxTagRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class TxTagService(
    private val txTagRepository: TxTagRepository
) {

    fun createTxTag(txTag: TxTag): TxTag {
        return txTagRepository.save(txTag)
    }

    @Transactional
    fun updateTxTag(txId: Long, memberId: Long, tagNames: String): TxTag {
        val txTag = txTagRepository.findByTxId(txId) ?: TxTag(txId, memberId, tagNames)
        txTag.tagNames = tagNames
        return this.createTxTag(txTag)
    }
}