package com.side.tiggle.domain.reaction.repository

import com.side.tiggle.domain.reaction.model.Reaction
import com.side.tiggle.domain.reaction.model.ReactionType
import org.springframework.data.jpa.repository.JpaRepository

interface ReactionRepository: JpaRepository<Reaction, Long> {
    fun findByTxIdAndSenderId(txId: Long, senderId: Long): Reaction?
    fun countAllByTxIdAndType(txId: Long, type: ReactionType): Int
    fun deleteByTxIdAndSenderId(txId: Long, senderId: Long): Int
}
