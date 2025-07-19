package com.side.tiggle.global.common.file

import jakarta.annotation.PostConstruct
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

/**
 * 파일 업로드 공통 추상 클래스
 * 도메인별 파일 업로드 유틸리티의 공통 기능을 제공
 *
 * @since 2025-07-19
 * @author 양병학
 */
enum class FileValidationError {
    EMPTY_FILE,
    FILE_SIZE_EXCEEDED,
    INVALID_FILE_TYPE,
    INVALID_EXTENSION,
    MISSING_FILENAME
}

/**
 * 파일 업로드 공통 추상 클래스
 * 도메인별 파일 업로드 유틸리티의 공통 기능을 제공
 */
abstract class AbstractFileUploadUtil {

    abstract val basePath: String
    abstract val maxSize: Long
    abstract val allowedTypes: List<String>

    protected open val allowedExtensions = listOf("jpg", "jpeg", "png", "gif")

    @PostConstruct
    fun validateConfiguration() {
        if (basePath.isBlank()) {
            throw IllegalStateException("업로드 경로가 설정되지 않았습니다")
        }

        if (maxSize <= 0) {
            throw IllegalStateException("최대 파일 크기가 올바르게 설정되지 않았습니다")
        }

        if (allowedTypes.isEmpty()) {
            throw IllegalStateException("허용된 파일 타입이 설정되지 않았습니다")
        }

        val uploadDir = File(basePath)
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw IllegalStateException("업로드 디렉토리를 생성할 수 없습니다: $basePath")
        }
    }

    /**
     * 파일 업로드 템플릿 메서드
     * 공통 업로드 로직을 제공하고, 구현체에서 도메인별 디렉토리 생성만 담당
     */
    fun uploadFile(file: MultipartFile, vararg pathSegments: String): String {
        validateFile(file)
        val extension = validateFileNameAndGetExtension(file)
        val targetDirectory = createTargetDirectory(*pathSegments)
        val safeFilename = generateSafeFilename(extension)
        val savePath = Paths.get(targetDirectory.absolutePath, safeFilename)

        return saveFileAndGetPath(file, savePath)
    }

    protected abstract fun createTargetDirectory(vararg pathSegments: String): File
    protected abstract fun createDomainException(errorType: FileValidationError, cause: Throwable? = null): RuntimeException

    protected fun validateFile(file: MultipartFile) {
        if (file.isEmpty) {
            throw createDomainException(FileValidationError.EMPTY_FILE)
        }

        if (file.size > maxSize) {
            throw createDomainException(FileValidationError.FILE_SIZE_EXCEEDED)
        }

        if (!allowedTypes.contains(file.contentType)) {
            throw createDomainException(FileValidationError.INVALID_FILE_TYPE)
        }
    }

    protected fun validateFileNameAndGetExtension(file: MultipartFile): String {
        val originalName = file.originalFilename ?: throw createDomainException(FileValidationError.MISSING_FILENAME)

        val extension = originalName.substringAfterLast('.', "")
        if (extension.isBlank()) {
            throw createDomainException(FileValidationError.INVALID_EXTENSION)
        }

        if (!allowedExtensions.contains(extension.lowercase())) {
            throw createDomainException(FileValidationError.INVALID_EXTENSION)
        }

        return extension.lowercase()
    }

    protected fun generateSafeFilename(extension: String): String {
        return "${System.currentTimeMillis()}_${UUID.randomUUID()}.$extension"
    }

    protected fun saveFileAndGetPath(file: MultipartFile, savePath: Path): String {
        try {
            file.transferTo(savePath)
        } catch (e: Exception) {
            throw IllegalStateException("파일 저장 중 오류가 발생했습니다: ${e.message}", e)
        }

        return savePath.toString()
    }
}