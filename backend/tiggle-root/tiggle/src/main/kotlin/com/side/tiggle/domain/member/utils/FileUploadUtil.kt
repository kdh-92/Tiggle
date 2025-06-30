package com.side.tiggle.domain.member.utils

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

@Component
@ConfigurationProperties(prefix = "part.upload.profile")
class FileUploadUtil {
    lateinit var path: String

    var maxSize: Long = 0

    lateinit var allowedTypes: List<String>

    fun uploadProfileImage(memberId: Long, file: MultipartFile): String {
        validateFile(file)
        val sanitizedFileName = sanitizeFileName(file.originalFilename ?: "profile.jpg")

        val uploadFolder = File(path, memberId.toString())
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs()
        }

        val savePath: Path = Paths.get(uploadFolder.absolutePath, sanitizedFileName)
        file.transferTo(savePath)

        return savePath.toString()
    }

    private fun validateFile(file: MultipartFile) {
        if (!allowedTypes.contains(file.contentType)) {
            throw IllegalArgumentException("허용되지 않는 파일 형식입니다.")
        }
        if (file.size > maxSize) {
            throw IllegalArgumentException("파일 크기가 너무 큽니다.")
        }
    }

    private fun sanitizeFileName(fileName: String): String {
        return fileName.replace("[^a-zA-Z0-9._-]".toRegex(), "_").take(100)
    }
}