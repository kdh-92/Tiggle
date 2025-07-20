package com.side.tiggle.domain.member.utils

import com.side.tiggle.domain.member.exception.MemberException
import com.side.tiggle.domain.member.exception.error.MemberErrorCode
import com.side.tiggle.global.common.file.AbstractFileUploadUtil // 추가: 추상 클래스 import
import com.side.tiggle.global.common.file.FileValidationError // 추가: enum import
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Component
@ConfigurationProperties(prefix = "part.upload.profile")
class MemberFileUploadUtil : AbstractFileUploadUtil() {

    lateinit var path: String
    override val basePath: String get() = path
    override var maxSize: Long = 0
    override lateinit var allowedTypes: List<String>

    fun uploadProfileImage(memberId: Long, file: MultipartFile): String {
        val savedPath = uploadFile(file, memberId.toString())

        return savedPath.replace("\\", "/").replace(basePath, "upload/profile")
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
            FileValidationError.EMPTY_FILE -> MemberException(MemberErrorCode.EMPTY_FILE)
            FileValidationError.FILE_SIZE_EXCEEDED -> MemberException(MemberErrorCode.FILE_SIZE_EXCEEDED)
            FileValidationError.INVALID_FILE_TYPE -> MemberException(MemberErrorCode.INVALID_FILE_TYPE)
            FileValidationError.INVALID_EXTENSION -> MemberException(MemberErrorCode.INVALID_FILE_TYPE)
            FileValidationError.MISSING_FILENAME -> MemberException(MemberErrorCode.INVALID_FILE_TYPE)
        }
    }

    fun deleteProfileImage(profileUrl: String?) {
        if (profileUrl != null && !profileUrl.startsWith("http")) {
            try {
                val absolutePath = if (profileUrl.startsWith("upload/profile/")) {
                    val relativePath = profileUrl.substringAfter("upload/profile/")
                    File(basePath, relativePath).absolutePath // 변경: path -> basePath
                } else {
                    profileUrl
                }

                val file = File(absolutePath)
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
