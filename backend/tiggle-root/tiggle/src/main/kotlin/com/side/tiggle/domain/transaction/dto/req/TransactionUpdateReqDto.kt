package com.side.tiggle.domain.transaction.dto.req

import com.side.tiggle.domain.transaction.model.TransactionType
import java.time.LocalDate

data class TransactionUpdateReqDto(
    val type: TransactionType,
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
    val assetId: Long,
    val categoryId: Long,
    val tagNames: String
)