package com.side.tiggle.domain.transaction.dto.view

import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto

data class TransactionRespDtoWithParent(
    val tx: TransactionRespDto,
    val parent: TransactionRespDto?
)
