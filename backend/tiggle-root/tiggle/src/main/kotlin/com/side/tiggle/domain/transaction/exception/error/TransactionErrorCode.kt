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

    TRANSACTION_ACCESS_DENIED(HttpStatus.FORBIDDEN, 60011, "해당 거래에 대한 권한이 없습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}