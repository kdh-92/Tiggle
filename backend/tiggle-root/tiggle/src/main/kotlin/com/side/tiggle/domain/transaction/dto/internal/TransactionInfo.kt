package com.side.tiggle.domain.transaction.dto.internal

import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.model.TxType
import java.time.LocalDate

data class TransactionInfo(
    val id: Long,
    val memberId: Long,
    val categoryId: Long,
    val txType: TxType,
    val imageUrls: String? = null,
    var amount: Int,
    var date: LocalDate,
    var content: String,
    var reason: String,
    var tagNames: List<String>? = null,
) {
    companion object {
        fun fromEntity(transaction: Transaction): TransactionInfo {
            return TransactionInfo(
                id = transaction.id!!,
                memberId = transaction.member.id,
                categoryId = transaction.category.id!!,
                txType = transaction.txType,
                imageUrls = transaction.imageUrls,
                amount = transaction.amount,
                date = transaction.date,
                content = transaction.content,
                reason = transaction.reason,
                tagNames = transaction.tagNames
            )
        }
    }
}
