package com.side.tiggle.domain.transaction.dto.resp

import com.side.tiggle.domain.member.dto.resp.MemberRespDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDate
import java.time.LocalDateTime

data class TransactionRespDto(
    val id: Long,
    val member: MemberRespDto,
    val category: CategoryRespDto,
    val tagNames: List<String>?,
    val createdAt: LocalDateTime,
    val parentId: Long? = null,
    val imageUrl: String?,
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
) {
    var parentTx: TransactionRespDto? = null
    var txUpCount: Int? = null
    var txDownCount: Int? = null
    var txCommentCount: Int? = null

    companion object {
        fun fromEntity(tx: Transaction): TransactionRespDto {
            return TransactionRespDto(
                id = tx.id!!,
                member = MemberRespDto.fromEntity(tx.member),
                category = CategoryRespDto.fromEntity(tx.category),
                imageUrl = tx.imageUrl,
                amount = tx.amount,
                date = tx.date,
                content = tx.content,
                reason = tx.reason,
                createdAt = tx.createdAt!!,
                tagNames = tx.tagNames
            )
        }

        fun fromEntityDetailTx(
            tx: Transaction,
        ): TransactionRespDto {
            val dto = this.fromEntity(tx)
            return dto
        }

        fun fromEntityWithCount(
            tx: Transaction,
            txUpCount: Int,
            txDownCount: Int,
            txCommentCount: Int
        ): TransactionRespDto {
            return fromEntity(tx)
                .apply {
                    this.txUpCount = txUpCount
                    this.txDownCount = txDownCount
                    this.txCommentCount = txCommentCount
                }
        }

        fun fromEntityPage(
            txPage: Page<Transaction>,
            dtoList: List<TransactionRespDto>
        ): Page<TransactionRespDto> {
            return PageImpl(
                dtoList,
                txPage.pageable,
                txPage.totalElements
            )
        }
    }
}
