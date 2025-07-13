package com.side.tiggle.domain.transaction.utils

import com.side.tiggle.domain.transaction.exception.TransactionException
import com.side.tiggle.domain.transaction.exception.error.TransactionErrorCode
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Component
@ConfigurationProperties(prefix = "part.upload.transaction")
class TransactionFileUploadUtil {
    //TODO: validation 검증필요, 프로퍼티 초기화 안전성 개선
    lateinit var path: String
    var maxSize: Long = 0
    lateinit var allowedTypes: List<String>

    @PostConstruct
    fun validateConfiguration() {
        if (!::path.isInitialized || path.isBlank()) {
            throw IllegalStateException("업로드 경로(part.upload.transaction.path)가 설정되지 않았습니다")
        }

        if (maxSize <= 0) {
            throw IllegalStateException("최대 파일 크기(part.upload.transaction.max-size)가 올바르게 설정되지 않았습니다")
        }

        if (!::allowedTypes.isInitialized || allowedTypes.isEmpty()) {
            throw IllegalStateException("허용된 파일 타입(part.upload.transaction.allowed-types)이 설정되지 않았습니다")
        }

        val uploadDir = File(path)
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw IllegalStateException("업로드 디렉토리를 생성할 수 없습니다: $path")
        }
    }

    fun uploadTransactionImage(file: MultipartFile): String {
        validateFile(file)
        val extension = validateFileNameAndGetExtension(file)

        val folderPath: String = createDateFolder()

        val safeFilename = "${System.currentTimeMillis()}_${UUID.randomUUID()}.$extension"
        val saveName = path + File.separator + folderPath + File.separator + safeFilename

        val savePath = Paths.get(saveName)

        try {
            file.transferTo(savePath)
        } catch (e: Exception) {
            throw IllegalStateException("파일 저장 중 오류가 발생했습니다: ${e.message}", e)
        }

        return saveName.replace("\\", "/")
    }

    private fun validateFileNameAndGetExtension(file: MultipartFile): String {
        val originalName: String = file.originalFilename ?: throw IllegalArgumentException("파일명이 없습니다")

        val extension = originalName.substringAfterLast('.', "")
        if (extension.isBlank()) {
            throw IllegalArgumentException("파일 확장자가 없습니다")
        }

        val allowedExtensions = listOf("jpg", "jpeg", "png", "gif")
        if (!allowedExtensions.contains(extension.lowercase())) {
            throw TransactionException(TransactionErrorCode.INVALID_FILE_TYPE)
        }

        return extension.lowercase()
    }

    private fun validateFile(file: MultipartFile) {
        if (!allowedTypes.contains(file.contentType)) {
            throw TransactionException(TransactionErrorCode.INVALID_FILE_TYPE)
        }

        if (file.size > maxSize) {
            throw TransactionException(TransactionErrorCode.FILE_SIZE_EXCEEDED)
        }

        if (file.isEmpty) {
            throw TransactionException(TransactionErrorCode.EMPTY_FILE)
        }
    }

    private fun createDateFolder(): String {
        val str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val folderPath = str.replace("/", File.separator)

        val uploadPathFolder = File(path, folderPath)
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs()
        }

        return folderPath
    }

    fun deleteEmptyDateFolder() {
        val str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val folderPath = str.replace("/", File.separator)
        val uploadPathFolder = File(path, folderPath)

        if (uploadPathFolder.exists()) {
            if (uploadPathFolder.list()?.isEmpty() == true) {
                uploadPathFolder.delete()
            }
        }
    }
}