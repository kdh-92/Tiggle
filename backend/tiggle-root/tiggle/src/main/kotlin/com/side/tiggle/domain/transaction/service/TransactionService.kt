package com.side.tiggle.domain.transaction.service

import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionListRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionPageRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface TransactionService {

    fun createTransaction(memberId: Long, dto: TransactionCreateReqDto, file: MultipartFile)
    fun updateTransaction(memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto)
    fun deleteTransaction(memberId: Long, txId: Long)
    fun getTransactionDetail(id: Long): TransactionRespDto
    fun getTransactionOrThrow(transactionId: Long): TransactionInfo
    fun getCountOffsetTransaction(pageSize: Int, index: Int): TransactionPageRespDto
    fun getMemberCountOffsetTransaction(
        memberId: Long,
        count: Int,
        offset: Int,
        startDate: LocalDate?,
        endDate: LocalDate?,
        categoryIds: List<Long>?,
        tagNames: List<String>?
    ): TransactionPageRespDto
}
