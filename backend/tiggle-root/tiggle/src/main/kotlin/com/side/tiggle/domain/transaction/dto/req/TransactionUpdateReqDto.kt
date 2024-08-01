package com.side.tiggle.domain.transaction.dto.req

import java.time.LocalDate

data class TransactionUpdateReqDto(
    val amount: Int,
    val date: LocalDate,
    val content: String,
    val reason: String,
    val assetId: Long,
    val categoryId: Long,
    val tagNames: List<String>
)