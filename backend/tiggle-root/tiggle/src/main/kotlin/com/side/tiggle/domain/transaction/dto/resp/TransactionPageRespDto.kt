package com.side.tiggle.domain.transaction.dto.resp

import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import org.springframework.data.domain.Page

data class TransactionPageRespDto(
    val transactions: List<TransactionRespDto>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Long,
    val totalPages: Int,
    val isLast: Boolean
) {
    companion object {
        fun fromPage(page: Page<TransactionDtoWithCount>): TransactionPageRespDto {
            return TransactionPageRespDto(
                transactions = page.content.map { it.dto },
                pageNumber = page.number,
                pageSize = page.size,
                totalElements = page.totalElements,
                totalPages = page.totalPages,
                isLast = page.isLast
            )
        }
    }
}
