package com.side.tiggle.domain.transaction.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionPageRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import com.side.tiggle.domain.transaction.exception.TransactionException
import com.side.tiggle.domain.transaction.exception.error.TransactionErrorCode
import com.side.tiggle.domain.transaction.mapper.TransactionMapper
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.domain.transaction.utils.TransactionFileUploadUtil
import com.side.tiggle.global.common.logging.KLog
import com.side.tiggle.global.common.logging.log
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate

@KLog
@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val memberService: MemberService,
    private val categoryService: CategoryService,
    private val transactionMapper: TransactionMapper,
    private val transactionFileUploadUtil: TransactionFileUploadUtil,
    private val objectMapper: ObjectMapper
) : TransactionService {

    @Transactional
    override fun createTransaction(
        memberId: Long,
        dto: TransactionCreateReqDto,
        files: List<MultipartFile>
    ) {
        var savedPaths: List<Path>? = null
        try {
            val uploadedFilePaths = transactionFileUploadUtil.uploadTransactionImages(files)
            savedPaths = uploadedFilePaths.map { Paths.get(it) }

            val imageUrlsJson = objectMapper.writeValueAsString(uploadedFilePaths)
            dto.imageUrls = imageUrlsJson

            val member = memberService.getMemberReference(memberId)
            val category = categoryService.getCategoryReference(dto.categoryId)

            transactionRepository.save(
                dto.toEntity(member, category)
            )
        } catch (e: Exception) {
            savedPaths?.forEach { Files.deleteIfExists(it) }
            transactionFileUploadUtil.deleteEmptyDateFolder()
            throw e
        }
    }

    @Transactional
    override fun updateTransaction(
        memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto
    ) {
        val transaction = transactionRepository.findByIdWithMemberAndCategory(transactionId)
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

        transactionRepository.save(transaction)
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
        val tagNamesJson = if (tagNames != null && tagNames.isNotEmpty()) {
            objectMapper.writeValueAsString(tagNames)
        } else {
            null
        }

        val memberTxPage = transactionRepository.findByMemberIdWithFilters(
            memberId = memberId,
            startDate = startDate,
            endDate = endDate,
            categoryIds = categoryIds,
            tagNamesJson = tagNamesJson,
            PageRequest.of(offset, count)
        )
        if (memberTxPage.isEmpty) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)
        }

        val dtoWithCountPage = memberTxPage.map { mapTxRespDto(it) }
        return TransactionPageRespDto.fromPage(dtoWithCountPage)
    }

    @Transactional
    override fun addTransactionPhotos(memberId: Long, transactionId: Long, files: List<MultipartFile>) {
        val transaction = transactionRepository.findByIdWithMemberAndCategory(transactionId)
            ?: throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)

        if (transaction.member.id != memberId) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_ACCESS_DENIED)
        }

        var newImagePaths: List<String>? = null
        try {
            newImagePaths = transactionFileUploadUtil.uploadTransactionImages(files)

            val existingPaths = if (!transaction.imageUrls.isNullOrEmpty()) {
                objectMapper.readValue(transaction.imageUrls, Array<String>::class.java).toList()
            } else {
                emptyList()
            }

            val allPaths = existingPaths + newImagePaths
            transaction.imageUrls = objectMapper.writeValueAsString(allPaths)

            transactionRepository.save(transaction)
        } catch (e: Exception) {
            newImagePaths?.forEach { path ->
                try {
                    Files.deleteIfExists(Paths.get(path))
                } catch (deleteError: Exception) {
                    log.debug("임시 파일 정리 실패: $path", deleteError)
                }
            }

            try {
                transactionFileUploadUtil.deleteEmptyDateFolder()
            } catch (folderDeleteError: Exception) {
                log.debug("임시 폴더 정리 실패", folderDeleteError)
            }

            throw e
        }
    }

    @Transactional
    override fun deleteTransactionPhoto(memberId: Long, transactionId: Long, photoIndex: Int) {
        val transaction = transactionRepository.findByIdWithMemberAndCategory(transactionId)
            ?: throw TransactionException(TransactionErrorCode.TRANSACTION_NOT_FOUND)

        if (transaction.member.id != memberId) {
            throw TransactionException(TransactionErrorCode.TRANSACTION_ACCESS_DENIED)
        }

        val imagePaths = if (!transaction.imageUrls.isNullOrEmpty()) {
            objectMapper.readValue(transaction.imageUrls, Array<String>::class.java).toMutableList()
        } else {
            throw TransactionException(TransactionErrorCode.PHOTO_NOT_FOUND)
        }

        if (photoIndex < 0 || photoIndex >= imagePaths.size) {
            throw TransactionException(TransactionErrorCode.PHOTO_NOT_FOUND)
        }

        if (imagePaths.size == 1) {
            throw TransactionException(TransactionErrorCode.MINIMUM_PHOTO_REQUIRED)
        }

        val pathToDelete = imagePaths[photoIndex]
        transactionFileUploadUtil.deleteTransactionImage(pathToDelete)

        imagePaths.removeAt(photoIndex)
        transaction.imageUrls = objectMapper.writeValueAsString(imagePaths)

        transactionRepository.save(transaction)
    }
}
