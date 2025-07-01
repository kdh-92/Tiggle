package com.side.tiggle.global.exception

import com.side.tiggle.global.exception.error.ErrorCode

/**
 * 인증/인가 관련 예외 클래스
 * JWT, OAuth2 등 인증 시스템에서 발생하는 예외 처리
 */
class AuthException : CustomException {

    private val errorCode: ErrorCode

    /**
     * 지정된 에러 코드로 예외를 생성
     */
    constructor(errorCode: ErrorCode) : super(errorCode) {
        this.errorCode = errorCode
    }

    /**
     * 지정된 에러 코드와 원인 예외로 예외를 생성
     */
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause) {
        this.errorCode = errorCode
    }

    /**
     * 예외에 해당하는 에러 코드를 반환
     */
    override fun getErrorCode(): ErrorCode = errorCode
}