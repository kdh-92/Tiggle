package com.side.tiggle.domain.notification.exception

import com.side.tiggle.global.exception.CustomException
import com.side.tiggle.global.exception.error.ErrorCode

/**
 * 알림(Notification) 도메인 예외 클래스
 * 알림 관련 비즈니스 로직 처리 중 발생하는 예외를 처리
 */
class NotificationException : CustomException {

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