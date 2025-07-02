package com.side.tiggle.domain.transaction.dto.resp

import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.transaction.model.Transaction
import java.time.LocalDate
import java.time.LocalDateTime

data class TransactionRespDto(
    val id: Long,
    val member: MemberRespDto,
    val categoryId: Long,
    val tagNames: List<String>?,
    val createdAt: LocalDateTime,
    val parentId: Long? = null,
    val imageUrl: String?,
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
) {
    companion object {
        fun fromEntity(tx: Transaction, member: MemberRespDto): TransactionRespDto {
            return TransactionRespDto(
                id = tx.id!!,
                member = member,
                categoryId = tx.categoryId,
                imageUrl = tx.imageUrl,
                amount = tx.amount,
                date = tx.date,
                content = tx.content,
                reason = tx.reason,
                createdAt = tx.createdAt!!,
                tagNames = tx.tagNames
            )
        }

        fun fromEntity(tx: Transaction): TransactionRespDto {
            return TransactionRespDto(
                id = tx.id!!,
                member = MemberRespDto.fromEntity(tx.member),
                categoryId = tx.categoryId,
                imageUrl = tx.imageUrl,
                amount = tx.amount,
                date = tx.date,
                content = tx.content,
                reason = tx.reason,
                createdAt = tx.createdAt!!,
                tagNames = tx.tagNames
            )
        }
    }

}
