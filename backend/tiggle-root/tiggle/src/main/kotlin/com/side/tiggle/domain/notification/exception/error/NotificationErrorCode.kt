package com.side.tiggle.domain.notification.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 알림(Notification) 도메인 에러 코드 정의
 * 알림 관련 예외 상황들에 대한 코드 및 메시지 제공
 */
enum class NotificationErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    // 조회 관련 오류 (70001~70010)
    NOTIFICATION_NOT_FOUND(HttpStatus.NOT_FOUND, 70001, "알림을 찾을 수 없습니다"),

    // 권한 관련 오류 (70011~70020)
    NOTIFICATION_ACCESS_DENIED(HttpStatus.FORBIDDEN, 70011, "해당 알림에 대한 권한이 없습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}