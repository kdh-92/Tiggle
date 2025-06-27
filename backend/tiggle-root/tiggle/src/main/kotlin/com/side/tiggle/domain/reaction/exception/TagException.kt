package com.side.tiggle.domain.reaction.exception

import com.side.tiggle.global.exception.CustomException
import com.side.tiggle.global.exception.error.ErrorCode

/**
 * 태그(Tag) 도메인 예외 클래스
 * 태그 관련 비즈니스 로직 처리 중 발생하는 예외를 처리
 */
class TagException : CustomException {

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