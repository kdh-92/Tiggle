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
    lateinit var path: String
    var maxSize: Long = 0
    lateinit var allowedTypes: List<String>

    fun uploadTransactionImage(file: MultipartFile): String {
        validateFile(file)

        val originalName: String = file.originalFilename ?: "transaction"
        val fileName = originalName.substringAfterLast('/').substringAfterLast('\\')
        val safeFileName = if (fileName.isBlank()) "uploaded_file" else fileName

        val folderPath: String = createDateFolder()
        val uuid = UUID.randomUUID().toString()
        val saveName = path + File.separator + folderPath + File.separator + uuid + "_" + safeFileName

        val savePath = Paths.get(saveName)
        file.transferTo(savePath)

        return saveName
    }

    private fun validateFile(file: MultipartFile) {
        if (!allowedTypes.contains(file.contentType)) {
            throw IllegalArgumentException("허용되지 않는 파일 형식입니다.")
        }

        if (file.size > maxSize) {
            throw IllegalArgumentException("파일 크기가 너무 큽니다.")
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
            if (uploadPathFolder.list().isEmpty()) {
                uploadPathFolder.delete()
            }
        }
    }
}