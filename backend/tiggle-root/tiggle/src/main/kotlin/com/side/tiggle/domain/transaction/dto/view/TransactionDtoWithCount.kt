package com.side.tiggle.domain.transaction.dto.view

import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto

data class TransactionDtoWithCount(
    val dto: TransactionRespDto,
    val upCount: Int,
    val downCount: Int,
    val commentCount: Int
)
