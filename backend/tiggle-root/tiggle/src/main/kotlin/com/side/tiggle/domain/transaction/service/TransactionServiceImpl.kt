package com.side.tiggle.domain.transaction.service

import com.side.tiggle.domain.transaction.dto.resp.TransactionRespDto
import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.domain.comment.service.CommentService
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.reaction.model.ReactionType
import com.side.tiggle.domain.reaction.service.ReactionService
import com.side.tiggle.domain.transaction.dto.req.TransactionCreateReqDto
import com.side.tiggle.domain.transaction.dto.req.TransactionUpdateReqDto
import com.side.tiggle.domain.transaction.model.Transaction
import com.side.tiggle.domain.transaction.repository.TransactionRepository
import com.side.tiggle.global.exception.NotFoundException
import org.springframework.data.domain.Page
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
class TransactionServiceImpl(
    private val memberService: MemberService,
    private val categoryService: CategoryService,
    private val transactionRepository: TransactionRepository,
    private val commentService: CommentService,
    private val reactionService: ReactionService
) : TransactionService {

    private val FOLDER_PATH = System.getProperty("user.dir") + "/upload/image"

    @Transactional
    override fun createTransaction(memberId: Long, dto: TransactionCreateReqDto, file: MultipartFile?): TransactionRespDto {
//        var savePath: Path? = null
        try {
//            val uploadedFilePath = uploadFileToFolder(file)
//            savePath = Paths.get(uploadedFilePath)
//            dto.imageUrl = uploadedFilePath

            val member = memberService.getMemberOrThrow(memberId)
            val category = categoryService.getCategory(dto.categoryId)
            val tx = transactionRepository.save(
                dto.toEntity(member, category)
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
    override fun updateTransaction(
        memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto
    ): TransactionRespDto {
        val transaction = transactionRepository.findById(transactionId)
            .filter { it.member.id == memberId }
            .orElseThrow { NotFoundException() }
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
    override fun deleteTransaction(memberId: Long, txId: Long) {
        transactionRepository.delete(
            transactionRepository.findById(txId).filter {
                it.member.id!! == memberId
            }.orElseThrow {
                NotFoundException()
            }
        )
    }

    /**
     * Controller API 응답용 - DTO 반환
     * @since 2025-06-22
     * @author 양병학
     */
    override fun getTransactionDetail(id: Long): TransactionRespDto {
        val transaction = getTransactionOrThrow(id)
        return TransactionRespDto.fromEntity(transaction)
    }

    /**
     * Transaction 내부 사용이 아닌 다른 엔티티 참조용이라 반환시 DTO 사용안함
     * @since 2025-06-22
     * @author 양병학
     */
    override fun getTransactionOrThrow(transactionId: Long): Transaction {
        return transactionRepository.findById(transactionId)
            .orElseThrow{ NotFoundException() }
    }

    /**
     * Transaction 응답 시 추가 정보(좋아요/댓글 수) 포함하여 DTO 생성
     * 기존 Controller에서 Service로 이관
     * @since 2025-06-22
     * @author 양병학
     */
    private fun mapTxRespDto(tx: Transaction): TransactionRespDto {
        val txId = tx.id!!
        val txDownCount = reactionService.getReactionCount(txId, ReactionType.DOWN)
        val txUpCount = reactionService.getReactionCount(txId, ReactionType.UP)
        val txCommentCount = commentService.getParentCount(txId)
        return TransactionRespDto.fromEntityWithCount(
            tx = tx,
            txUpCount = txUpCount,
            txDownCount = txDownCount,
            txCommentCount = txCommentCount
        )
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

    override fun getCountOffsetTransaction(
        pageSize: Int,
        index: Int
    ): Page<TransactionRespDto> {
        val txPage = transactionRepository.findAll(
            PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
        if (txPage.isEmpty) {
            throw NotFoundException()
        }

        return txPage.map { mapTxRespDto(it) }
    }

    override fun getMemberCountOffsetTransaction(
        memberId: Long,
        count: Int,
        offset: Int,
        startDate: LocalDate?,
        endDate: LocalDate?,
        categoryIds: List<Long>?,
        tagNames: List<String>?
    ): Page<TransactionRespDto> {
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

        return memberTxPage.map { mapTxRespDto(it) }
    }

    override fun getAllUndeletedTransaction(): List<TransactionRespDto> {
        val tx = transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))

        return tx.map { mapTxRespDto(it) }
    }
}