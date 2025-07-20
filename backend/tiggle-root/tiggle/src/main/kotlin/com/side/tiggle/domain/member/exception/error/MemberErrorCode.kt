package com.side.tiggle.domain.member.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 회원(Member) 도메인 에러 코드 정의
 * 회원 관련 예외 상황들에 대한 코드 및 메시지 제공
 */
enum class MemberErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {

    // 조회 관련 오류 (10001~10010)
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 10001, "회원을 찾을 수 없습니다"),

    // 파일 관련 오류 (10011~10020)
    PROFILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 10011, "프로필 이미지 업로드에 실패했습니다"),
    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, 10012, "허용되지 않은 파일 형식입니다"),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, 10013, "파일 크기 제한을 초과했습니다"),
    EMPTY_FILE(HttpStatus.BAD_REQUEST, 10014, "빈 파일은 업로드할 수 없습니다"),
    PROFILE_IMAGE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 10015, "기존 프로필 이미지 삭제에 실패했습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}