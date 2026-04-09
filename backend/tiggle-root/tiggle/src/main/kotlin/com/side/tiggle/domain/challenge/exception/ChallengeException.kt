package com.side.tiggle.domain.challenge.exception

import com.side.tiggle.global.exception.CustomException
import com.side.tiggle.global.exception.error.ErrorCode

/**
 * 챌린지(Challenge) 도메인 예외 클래스
 * 챌린지 관련 비즈니스 로직 처리 중 발생하는 예외를 처리
 */
class ChallengeException : CustomException {

    private val errorCode: ErrorCode

    constructor(errorCode: ErrorCode) : super(errorCode) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause) {
        this.errorCode = errorCode
    }

    override fun getErrorCode(): ErrorCode = errorCode
}
