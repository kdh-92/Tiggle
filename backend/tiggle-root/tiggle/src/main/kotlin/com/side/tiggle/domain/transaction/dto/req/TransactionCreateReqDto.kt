package com.side.tiggle.domain.transaction.dto.req

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
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
        member: Member,
        category: Category
    ): Transaction {
        return Transaction(
            member = member,
            category = category,
            imageUrl = this.imageUrl,
            amount = this.amount,
            date = this.date,
            content = this.content,
            reason = this.reason,
            tagNames = this.tagNames
        )
    }
}