package com.side.tiggle.domain.transaction.service

import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionListRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionPageRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import com.side.tiggle.domain.transaction.exception.TransactionException
import com.side.tiggle.domain.transaction.exception.error.TransactionErrorCode
import com.side.tiggle.domain.transaction.mapper.TransactionMapper
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.domain.transaction.utils.TransactionFileUploadUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val memberService: MemberService,
    private val categoryService: CategoryService,
    private val transactionMapper: TransactionMapper,
    private val transactionFileUploadUtil: TransactionFileUploadUtil
) : TransactionService {

    @Transactional
    override fun createTransaction(
        memberId: Long,
        dto: TransactionCreateReqDto,
        file: MultipartFile?
    ): TransactionRespDto {
//        var savePath: Path? = null
        try {
//            val uploadedFilePath = transactionFileUploadUtil.uploadTransactionImage(file)
//            savePath = Paths.get(uploadedFilePath)
//            dto.imageUrl = uploadedFilePath

            val member = memberService.getMemberReference(memberId)
            val category = categoryService.getCategoryReference(dto.categoryId)

            val tx = transactionRepository.save(
                dto.toEntity(member, category)
            )

            return TransactionRespDto.fromEntity(tx)
        } catch (e: Exception) {
//            if (savePath != null) {
//                Files.deleteIfExists(savePath)
//            }
//            transactionFileUploadUtil.deleteEmptyDateFolder()
            throw e
        }
    }

    @Transactional
    override fun updateTransaction(
        memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto
    ): TransactionRespDto {
        val transaction = transactionRepository.findByIdWithMemberAndCategory(transactionId)  // ← 변경
            ?: throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)

        if (transaction.member.id != memberId) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_ACCESS_DENIED)
        }

        transaction.apply {
            amount = dto.amount
            date = dto.date
            content = dto.content
            reason = dto.reason
            tagNames = dto.tagNames ?: emptyList()
        }

        val tx = transactionRepository.save(transaction)
        return TransactionRespDto.fromEntity(tx)
    }

    @Transactional
    override fun deleteTransaction(memberId: Long, txId: Long) {
        val transaction = transactionRepository.findById(txId)
            .orElseThrow { TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND) }

        if (transaction.member.id != memberId) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_ACCESS_DENIED)
        }

        transactionRepository.delete(transaction)
    }

    /**
     * Controller API 응답용 - DTO 반환
     * @since 2025-06-22
     * @author 양병학
     */
    override fun getTransactionDetail(id: Long): TransactionRespDto {
        val tx = getTransactionEntityOrThrow(id)

        return TransactionRespDto.fromEntity(tx)
    }

    /**
     * Member와 동일하게 Transaction도 내부용, 외부용 메서드로 분리 & 내부 반환용 별도 DTO(/internal/TransactionInfo) 사용하도록 수정
     * @since 2025-06-25
     * @author 권동현
     */
    override fun getTransactionOrThrow(transactionId: Long): TransactionInfo {
        val transaction = getTransactionEntityOrThrow(transactionId)
        return TransactionInfo.fromEntity(transaction)
    }

    private fun getTransactionEntityOrThrow(transactionId: Long): Transaction {
        return transactionRepository.findByIdWithMemberAndCategory(transactionId)
            ?: throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)
    }

    /**
     * Transaction 응답 시 추가 정보(좋아요/댓글 수) 포함하여 DTO 생성
     * 기존 Controller에서 Service로 이관
     * @since 2025-06-22
     * @author 양병학
     */
    private fun mapTxRespDto(tx: Transaction): TransactionDtoWithCount {
        return transactionMapper.toDtoWithCount(tx)
    }

    override fun getCountOffsetTransaction(
        pageSize: Int,
        index: Int
    ): TransactionPageRespDto {
        val txPage = transactionRepository.findAllWithMemberAndCategoryPaged(
            PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
        if (txPage.isEmpty) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)
        }
        val dtoWithCountPage = txPage.map { mapTxRespDto(it) }
        return TransactionPageRespDto.fromPage(dtoWithCountPage)
    }

    override fun getMemberCountOffsetTransaction(
        memberId: Long,
        count: Int,
        offset: Int,
        startDate: LocalDate?,
        endDate: LocalDate?,
        categoryIds: List<Long>?,
        tagNames: List<String>?
    ): TransactionPageRespDto {
        val memberTxPage = transactionRepository.findByMemberIdWithFilters(
            memberId = memberId,
            startDate = startDate,
            endDate = endDate,
            categoryIds = categoryIds,
            tagNames = tagNames,
            PageRequest.of(offset, count, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
        if (memberTxPage.isEmpty) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)
        }

        val dtoWithCountPage = memberTxPage.map { mapTxRespDto(it) }
        return TransactionPageRespDto.fromPage(dtoWithCountPage)
    }

    override fun getAllUndeletedTransaction(): TransactionListRespDto {
        val tx = transactionRepository.findAllWithMemberAndCategory()
        val dtoList = tx.map { TransactionRespDto.fromEntity(it) }
        return TransactionListRespDto(dtoList)
    }
}