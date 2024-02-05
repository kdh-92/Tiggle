package com.side.tiggle.domain.transaction.dto.resp

import com.side.tiggle.domain.asset.dto.AssetDto
import com.side.tiggle.domain.asset.dto.resp.AssetRespDto
import com.side.tiggle.domain.category.dto.CategoryDto
import com.side.tiggle.domain.category.dto.resp.CategoryRespDto
import com.side.tiggle.domain.member.dto.controller.MemberResponseDto
import com.side.tiggle.domain.member.dto.service.MemberDto
import com.side.tiggle.domain.transaction.dto.TransactionDto
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.model.TransactionType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDate
import java.time.LocalDateTime

data class TransactionRespDto(
    val id: Long,
    val member: MemberResponseDto,
    val asset: AssetRespDto,
    val category: CategoryRespDto,
    val txTagNames: String,
    val createdAt: LocalDateTime,
    val parentId: Long? = null,
    val type: TransactionType,
    val imageUrl: String,
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
) {
    var parentTx: TransactionDto? = null
    var txUpCount: Int? = null
    var txDownCount: Int? = null
    var txCommentCount: Int? = null

    companion object {
        fun fromEntity(tx: Transaction): TransactionRespDto {
            return TransactionRespDto(
                id = tx.id!!,
                member = MemberDto.fromEntityToMemberResponseDto(tx.member),
                asset = AssetRespDto.fromEntity(tx.asset),
                category = CategoryRespDto.fromEntity(tx.category),
                type = tx.type,
                imageUrl = tx.imageUrl,
                amount = tx.amount,
                date = tx.date,
                content = tx.content,
                reason = tx.reason,
                createdAt = tx.createdAt!!,
                txTagNames = tx.tagNames
            )
        }

        fun fromEntityDetailTx(
            tx: Transaction,
            parentTx: Transaction?
        ): TransactionRespDto {
            val dto = this.fromEntity(tx)
            if (parentTx != null) {
                dto.parentTx = TransactionDto.fromEntity(tx)
            }
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