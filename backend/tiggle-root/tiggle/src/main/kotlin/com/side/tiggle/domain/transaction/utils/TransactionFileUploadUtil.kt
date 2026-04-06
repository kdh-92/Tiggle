package com.side.tiggle.domain.transaction.utils

import com.side.tiggle.domain.transaction.exception.TransactionException
import com.side.tiggle.domain.transaction.exception.error.TransactionErrorCode
import com.side.tiggle.global.common.file.AbstractFileUploadUtil
import com.side.tiggle.global.common.file.FileValidationError
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
@ConfigurationProperties(prefix = "part.upload.transaction")
class TransactionFileUploadUtil : AbstractFileUploadUtil() {

    lateinit var path: String
    override val basePath: String get() = path
    override var maxSize: Long = 0
    override lateinit var allowedTypes: List<String>
    private val folderDeletionLock = Any()

    fun uploadTransactionImage(file: MultipartFile): String {
        val folderPath = createDateFolder()
        return uploadFile(file, folderPath).replace("\\", "/")
    }

    fun uploadTransactionImages(files: List<MultipartFile>): List<String> {
        return files.map { file ->
            uploadTransactionImage(file)
        }
    }

    override fun createTargetDirectory(vararg pathSegments: String): File {
        val targetPath = if (pathSegments.isNotEmpty()) {
            File(basePath, pathSegments.joinToString(File.separator))
        } else {
            File(basePath)
        }

        if (!targetPath.exists()) {
            targetPath.mkdirs()
        }

        return targetPath
    }

    override fun createDomainException(errorType: FileValidationError, cause: Throwable?): RuntimeException {
        return when (errorType) {
            FileValidationError.EMPTY_FILE -> TransactionException(TransactionErrorCode.EMPTY_FILE)
            FileValidationError.FILE_SIZE_EXCEEDED -> TransactionException(TransactionErrorCode.FILE_SIZE_EXCEEDED)
            FileValidationError.INVALID_FILE_TYPE -> TransactionException(TransactionErrorCode.INVALID_FILE_TYPE)
            FileValidationError.INVALID_EXTENSION -> TransactionException(TransactionErrorCode.INVALID_FILE_TYPE)
            FileValidationError.MISSING_FILENAME -> TransactionException(TransactionErrorCode.INVALID_FILE_TYPE)
        }
    }

    private fun createDateFolder(): String {
        val str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        val folderPath = str.replace("/", File.separator)

        val uploadPathFolder = File(basePath, folderPath)
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs()
        }

        return folderPath
    }

    fun deleteTransactionImage(imagePath: String): Boolean {
        return try{
            Files.deleteIfExists(Paths.get(imagePath))
        } catch (e: Exception){
            throw TransactionException(TransactionErrorCode.PHOTO_DELETE_FAILED, e)
        }
    }

    fun deleteEmptyDateFolder() {
        synchronized(folderDeletionLock) {
            val str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            val folderPath = str.replace("/", File.separator)
            val uploadPathFolder = File(basePath, folderPath)

            if (uploadPathFolder.exists()) {
                if (uploadPathFolder.list()?.isEmpty() == true) {
                    uploadPathFolder.delete()
                }
            }
        }
    }
}
