package com.side.tiggle.domain.transaction.dto

import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
import java.time.LocalDate

data class TransactionDto(
    val memberId: Long,
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

    companion object {
        fun fromEntity(tx: Transaction) : TransactionDto {
            return TransactionDto(
                memberId = tx.member.id,
                categoryId = tx.category.id!!,
                imageUrl = tx.imageUrl,
                amount = tx.amount,
                date = tx.date,
                content = tx.content,
                reason = tx.reason,
                tagNames = tx.tagNames
            )
        }
    }
}