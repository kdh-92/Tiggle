package com.side.tiggle.global.exception

import com.side.tiggle.global.exception.error.ErrorCode

/**
 * 사용자 정의 예외의 추상 클래스
 * 애플리케이션 전역에서 사용하는 공통 예외 상위 타입
 */
abstract class CustomException : RuntimeException {

    /**
     * 메시지만 포함하는 기본 생성자
     */
    constructor(errorCode: ErrorCode) : super(errorCode.message())

    /**
     * 메시지 + 원인 예외 포함하는 생성자
     */
    constructor(errorCode: ErrorCode, cause: Throwable) : super(errorCode.message(), cause)

    /**
     * 에러 코드 반환
     * @return ErrorCode 구현체
     */
    abstract fun getErrorCode(): ErrorCode
}