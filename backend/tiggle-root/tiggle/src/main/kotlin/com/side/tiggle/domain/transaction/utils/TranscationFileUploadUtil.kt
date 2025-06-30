package com.side.tiggle.domain.transaction.utils

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

        return saveName
    }

    private fun validateFileNameAndGetExtension(file: MultipartFile): String {
        val originalName: String = file.originalFilename ?: throw IllegalArgumentException("파일명이 없습니다")

        val extension = originalName.substringAfterLast('.', "")
        if (extension.isBlank()) {
            throw IllegalArgumentException("파일 확장자가 없습니다")
        }

        val allowedExtensions = listOf("jpg", "jpeg", "png", "gif")
        if (!allowedExtensions.contains(extension.lowercase())) {
            throw IllegalArgumentException("허용되지 않은 파일 형식입니다: $extension")
        }

        return extension.lowercase()
    }

    private fun validateFile(file: MultipartFile) {
        if (!allowedTypes.contains(file.contentType)) {
            throw IllegalArgumentException("허용되지 않는 MIME 타입입니다: ${file.contentType}")
        }

        if (file.size > maxSize) {
            throw IllegalArgumentException("파일 크기가 ${maxSize / 1024 / 1024}MB를 초과합니다")
        }

        if (file.isEmpty) {
            throw IllegalArgumentException("빈 파일은 업로드할 수 없습니다")
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