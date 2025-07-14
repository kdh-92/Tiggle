package com.side.tiggle.domain.member.utils

import com.side.tiggle.domain.member.exception.MemberException
import com.side.tiggle.domain.member.exception.error.MemberErrorCode
import jakarta.annotation.PostConstruct
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Component
@ConfigurationProperties(prefix = "part.upload.profile")
class FileUploadUtil {
    lateinit var path: String
    var maxSize: Long = 0
    lateinit var allowedTypes: List<String>

    @PostConstruct
    fun validateConfiguration() {
        if (!::path.isInitialized || path.isBlank()) {
            throw IllegalStateException("업로드 경로(part.upload.profile.path)가 설정되지 않았습니다")
        }

        if (maxSize <= 0) {
            throw IllegalStateException("최대 파일 크기(part.upload.profile.max-size)가 올바르게 설정되지 않았습니다")
        }

        if (!::allowedTypes.isInitialized || allowedTypes.isEmpty()) {
            throw IllegalStateException("허용된 파일 타입(part.upload.profile.allowed-types)이 설정되지 않았습니다")
        }

        val uploadDir = File(path)
        if (!uploadDir.exists() && !uploadDir.mkdirs()) {
            throw IllegalStateException("업로드 디렉토리를 생성할 수 없습니다: $path")
        }
    }

    fun uploadProfileImage(memberId: Long, file: MultipartFile): String {
        validateFile(file)
        val extension = validateFileNameAndGetExtension(file)

        val uploadFolder = File(path, memberId.toString())
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }

        val safeFilename = "${System.currentTimeMillis()}_${UUID.randomUUID()}.$extension"
        val savePath: Path = Paths.get(uploadFolder.absolutePath, safeFilename)

        try {
            file.transferTo(savePath)
        } catch (e: Exception) {
            throw IllegalStateException("프로필 이미지 저장 중 오류가 발생했습니다: ${e.message}", e)
        }

        return savePath.toString()
    }

    private fun validateFileNameAndGetExtension(file: MultipartFile): String {
        val originalName: String = file.originalFilename ?: throw IllegalArgumentException("파일명이 없습니다")

        val extension = originalName.substringAfterLast('.', "")
        if (extension.isBlank()) {
            throw IllegalArgumentException("파일 확장자가 없습니다")
        }

        val allowedExtensions = listOf("jpg", "jpeg", "png", "gif")
        if (!allowedExtensions.contains(extension.lowercase())) {
            throw MemberException(MemberErrorCode.INVALID_FILE_TYPE)
        }

        return extension.lowercase()
    }

    private fun validateFile(file: MultipartFile) {
        if (!allowedTypes.contains(file.contentType)) {
            throw MemberException(MemberErrorCode.INVALID_FILE_TYPE)
        }

        if (file.size > maxSize) {
            throw MemberException(MemberErrorCode.FILE_SIZE_EXCEEDED)
        }

        if (file.isEmpty) {
            throw MemberException(MemberErrorCode.EMPTY_FILE)
        }
    }

    fun deleteProfileImage(profileUrl: String?) {
        if (profileUrl != null && !profileUrl.startsWith("http")) {
            try {
                val file = File(profileUrl)
                if (file.exists() && !file.delete()) {
                    throw MemberException(MemberErrorCode.PROFILE_IMAGE_DELETE_FAILED)
                }
            } catch (e: Exception) {
                if (e is MemberException) throw e
                throw MemberException(MemberErrorCode.PROFILE_IMAGE_DELETE_FAILED, e)
            }
        }
    }
}