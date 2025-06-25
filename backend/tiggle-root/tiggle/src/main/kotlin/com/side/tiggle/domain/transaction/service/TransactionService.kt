package com.side.tiggle.domain.transaction.service

import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.transaction.dto.internal.TransactionInfo
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionListRespDto
import com.side.tiggle.domain.transaction.dto.resp.TransactionPageRespDto
import com.side.tiggle.domain.transaction.dto.view.TransactionDtoWithCount
import com.side.tiggle.domain.transaction.mapper.TransactionMapper
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.global.exception.NotAuthorizedException
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val transactionMapper: TransactionMapper
) {

    private val FOLDER_PATH = System.getProperty("user.dir") + "/upload/image"

    @Transactional
    fun createTransaction(memberId: Long, dto: TransactionCreateReqDto, file: MultipartFile?): TransactionRespDto {
//        var savePath: Path? = null
        try {
//            val uploadedFilePath = uploadFileToFolder(file)
//            savePath = Paths.get(uploadedFilePath)
//            dto.imageUrl = uploadedFilePath

            val tx = transactionRepository.save(
                dto.toEntity(memberId, dto.categoryId)
            )

            return TransactionRespDto.fromEntity(tx)
        } catch (e: Exception) {
//            if (savePath != null) {
//                Files.deleteIfExists(savePath)
//            }
//            deleteFolder()
            throw e
        }
    }

    @Transactional
    fun updateTransaction(
        memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto
    ): TransactionRespDto {
        val transaction = transactionRepository.findById(transactionId)
            .orElseThrow { NotFoundException() }

        if (transaction.memberId != memberId) {
            throw NotAuthorizedException()
        }

        transaction.apply {
            amount = dto.amount
            date = dto.date
            content = dto.content
            reason = dto.reason
            tagNames = dto.tagNames
        }
        val tx = transactionRepository.save(transaction)
        return TransactionRespDto.fromEntity(tx)
    }

    @Transactional
    fun deleteTransaction(memberId: Long, txId: Long) {
        val transaction = transactionRepository.findById(txId)
            .orElseThrow { NotFoundException() }

        if (transaction.memberId != memberId) {
            throw NotAuthorizedException()
        }

        transactionRepository.delete(transaction)
    }

    /**
     * Controller API 응답용 - DTO 반환
     * @since 2025-06-22
     * @author 양병학
     */
    fun getTransactionDetail(id: Long): TransactionRespDto {
        val transaction = getTransactionEntityOrThrow(id)
        return TransactionRespDto.fromEntity(transaction)
    }

    /**
     * Member와 동일하게 Transaction도 내부용, 외부용 메서드로 분리 & 내부 반환용 별도 DTO(/internal/TransactionInfo) 사용하도록 수정
     * @since 2025-06-25
     * @author 권동현
     */
    fun getTransactionOrThrow(transactionId: Long): TransactionInfo {
        val transaction = getTransactionEntityOrThrow(transactionId)
        return TransactionInfo.fromEntity(transaction)
    }

    private fun getTransactionEntityOrThrow(transactionId: Long): Transaction {
        return transactionRepository.findById(transactionId)
            .orElseThrow{ NotFoundException() }
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

    fun uploadFileToFolder(uploadFile: MultipartFile): String {
        val originalName: String = uploadFile.originalFilename!!
        val fileName = originalName.substring(originalName.lastIndexOf("//") + 1)
        val folderPath: String = makeFolder()
        val uuid = UUID.randomUUID().toString()
        val saveName = FOLDER_PATH + File.separator + folderPath + File.separator + uuid + "_" + fileName

        val savePath = Paths.get(saveName)

        uploadFile.transferTo(savePath)
        return saveName
    }

    private fun makeFolder(): String {
        val str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val folderPath = str.replace("/", File.separator)

        val uploadPathFolder = File(FOLDER_PATH, folderPath)

        if (!uploadPathFolder.exists()) uploadPathFolder.mkdirs()
        return folderPath
    }

    private fun deleteFolder() {
        val str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val folderPath = str.replace("/", File.separator)
        val uploadPathFolder = File(FOLDER_PATH, folderPath)

        if (uploadPathFolder.exists()) {
            if (uploadPathFolder.list().isEmpty()) {
                uploadPathFolder.delete()
            }
        }
    }

    fun getCountOffsetTransaction(
        pageSize: Int,
        index: Int
    ): TransactionPageRespDto {
        val txPage = transactionRepository.findAll(
            PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
        if (txPage.isEmpty) {
            throw NotFoundException()
        }
        val dtoWithCountPage = txPage.map { mapTxRespDto(it) }
        return TransactionPageRespDto.fromPage(dtoWithCountPage)
    }

    fun getMemberCountOffsetTransaction(
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
            throw NotFoundException()
        }

        val dtoWithCountPage = memberTxPage.map { mapTxRespDto(it) }
        return TransactionPageRespDto.fromPage(dtoWithCountPage)
    }

    fun getAllUndeletedTransaction(): TransactionListRespDto {
        val tx = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
        val dtoList = tx.map { TransactionRespDto.fromEntity(it) }
        return TransactionListRespDto(dtoList)
    }
}

