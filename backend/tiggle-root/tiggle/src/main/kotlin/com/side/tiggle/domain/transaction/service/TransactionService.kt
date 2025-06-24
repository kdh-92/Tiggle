package com.side.tiggle.domain.transaction.service

import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.model.Transaction
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

interface TransactionService {

    fun createTransaction(memberId: Long, dto: TransactionCreateReqDto, file: MultipartFile?): TransactionRespDto

    fun updateTransaction(memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto): TransactionRespDto

    fun deleteTransaction(memberId: Long, txId: Long)

    fun getTransactionDetail(id: Long): TransactionRespDto

    fun getTransactionOrThrow(transactionId: Long): Transaction

    fun getCountOffsetTransaction(pageSize: Int, index: Int): Page<TransactionRespDto>

    fun getMemberCountOffsetTransaction(
        memberId: Long,
        count: Int,
        offset: Int,
        startDate: LocalDate?,
        endDate: LocalDate?,
        categoryIds: List<Long>?,
        tagNames: List<String>?
    ): Page<TransactionRespDto>

    fun getAllUndeletedTransaction(): List<TransactionRespDto>
}