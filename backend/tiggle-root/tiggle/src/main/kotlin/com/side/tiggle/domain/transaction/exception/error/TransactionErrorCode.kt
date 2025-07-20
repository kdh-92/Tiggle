package com.side.tiggle.domain.transaction.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 거래(Transaction) 도메인 에러 코드 정의
 * 거래 관련 예외 상황들에 대한 코드 및 메시지 제공
 */
enum class TransactionErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {

    TRANSACTION_NOT_FOUND(HttpStatus.NOT_FOUND, 60001, "거래 정보를 찾을 수 없습니다"),

    TRANSACTION_ACCESS_DENIED(HttpStatus.FORBIDDEN, 60011, "해당 거래에 대한 권한이 없습니다"),

    INVALID_FILE_TYPE(HttpStatus.BAD_REQUEST, 60021, "허용되지 않은 파일 형식입니다"),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, 60022, "파일 크기 제한을 초과했습니다"),
    EMPTY_FILE(HttpStatus.BAD_REQUEST, 60023, "빈 파일은 업로드할 수 없습니다"),
    
    PHOTO_NOT_FOUND(HttpStatus.NOT_FOUND, 60031, "사진을 찾을 수 없습니다"),
    PHOTO_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 60032, "사진 삭제에 실패했습니다"),
    MINIMUM_PHOTO_REQUIRED(HttpStatus.BAD_REQUEST, 60033, "최소 1장의 사진이 필요합니다");
    
    

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}