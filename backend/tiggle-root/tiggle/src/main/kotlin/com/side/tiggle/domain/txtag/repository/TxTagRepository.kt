package com.side.tiggle.domain.txtag.repository

import com.side.tiggle.domain.txtag.model.TxTag
import org.springframework.data.jpa.repository.JpaRepository

interface TxTagRepository: JpaRepository<TxTag, Long> {
    fun findByTxId(txId: Long?): TxTag?
}