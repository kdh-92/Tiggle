package com.side.tiggle.domain.character.exception

import com.side.tiggle.global.exception.CustomException
import com.side.tiggle.global.exception.error.ErrorCode

/**
 * 캐릭터(Character) 도메인 예외 클래스
 * 캐릭터 관련 비즈니스 로직 처리 중 발생하는 예외를 처리
 */
class CharacterException : CustomException {

    private val errorCode: ErrorCode

    constructor(errorCode: ErrorCode) : super(errorCode) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode, cause) {
        this.errorCode = errorCode
    }

    override fun getErrorCode(): ErrorCode = errorCode
}
