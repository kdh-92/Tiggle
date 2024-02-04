package com.side.tiggle.domain.transaction.dto

import com.side.tiggle.domain.asset.model.Asset
import com.side.tiggle.domain.category.model.Category
import com.side.tiggle.domain.member.model.Member
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.model.TransactionType
import java.time.LocalDate

data class TransactionDto(
    val memberId: Long,
    val parentId: Long? = null,
    val assetId: Long,
    val categoryId:Long,
    val type: TransactionType,
    var imageUrl: String,
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
    val tagNames: String
) {

    fun toEntity(
        member: Member,
        asset: Asset,
        category: Category
    ): Transaction {
        return Transaction(
            member = member,
            asset = asset,
            category = category,
            parentId = this.parentId,
            type = this.type,
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
                parentId = tx.parentId,
                assetId = tx.asset.id,
                categoryId = tx.category.id,
                type = tx.type,
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