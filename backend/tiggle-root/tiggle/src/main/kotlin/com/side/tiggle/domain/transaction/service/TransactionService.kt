package com.side.tiggle.domain.transaction.service

import com.side.tiggle.domain.category.service.CategoryService
import com.side.tiggle.domain.member.service.MemberService
import com.side.tiggle.domain.transaction.dto.TransactionDto
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
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class TransactionService(
    private val memberService: MemberService,
    private val categoryService: CategoryService,
    private val transactionRepository: TransactionRepository
) {

    private val FOLDER_PATH = System.getProperty("user.dir") + "/upload/image"

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

    @Transactional
    fun createTransaction(memberId: Long, dto: TransactionDto, file: MultipartFile?): Transaction {
        var savePath: Path? = null
        try {
//            val uploadedFilePath = uploadFileToFolder(file)
//            savePath = Paths.get(uploadedFilePath)
//            dto.imageUrl = uploadedFilePath

            val member = memberService.getMember(memberId)
            val category = categoryService.getCategory(dto.categoryId)
            val tx = transactionRepository.save(
                dto.toEntity(member, category)
            )

            return tx
        } catch (e: Exception) {
            if (savePath != null) {
                Files.deleteIfExists(savePath)
            }
            deleteFolder()
            throw e
        }
    }

    fun getTransaction(transactionId: Long): Transaction {
        return transactionRepository.findById(transactionId)
            .orElseThrow{ NotFoundException() }
    }

    fun getCountOffsetTransaction(
        pageSize: Int,
        index: Int
    ): Page<Transaction> {
        val txPage = transactionRepository.findAll(
            PageRequest.of(index, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
        if (txPage.isEmpty) {
            throw NotFoundException()
        }
        return txPage
    }

    fun getMemberCountOffsetTransaction(
        memberId: Long, count: Int, offset: Int
    ): Page<Transaction> {
        val memberTxPage = transactionRepository.findByMemberId(
            memberId, PageRequest.of(offset, count, Sort.by(Sort.Direction.DESC, "createdAt"))
        )
        if (memberTxPage.isEmpty) {
            throw NotFoundException()
        }
        return memberTxPage
    }

    fun getAllUndeletedTransaction(): List<Transaction> {
        return transactionRepository.findAll()
    }

    @Transactional
    fun updateTransaction(
        memberId: Long, transactionId: Long, dto: TransactionUpdateReqDto
    ): Transaction {
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

        return transactionRepository.save(transaction)
    }

    fun deleteTransaction(memberId: Long, txId: Long) {
        transactionRepository.delete(
            transactionRepository.findById(txId).filter {
                it.member.id!! == memberId
            }.orElseThrow {
                NotFoundException()
            }
        )
    }
}