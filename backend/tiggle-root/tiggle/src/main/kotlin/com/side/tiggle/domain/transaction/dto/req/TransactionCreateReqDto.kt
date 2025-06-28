package com.side.tiggle.domain.transaction.dto.req

import com.side.tiggle.domain.transaction.model.Transaction
import java.time.LocalDate

data class TransactionCreateReqDto(
    val categoryId:Long,
    var imageUrl: String?,
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
    val tagNames: List<String>?
) {

    fun toEntity(
        memberId: Long,
        categoryId: Long
    ): Transaction {
        return Transaction(
            memberId = memberId,
            categoryId = categoryId,
            imageUrl = this.imageUrl,
            amount = this.amount,
            date = this.date,
            content = this.content,
            reason = this.reason,
            tagNames = this.tagNames
        )
    }
}