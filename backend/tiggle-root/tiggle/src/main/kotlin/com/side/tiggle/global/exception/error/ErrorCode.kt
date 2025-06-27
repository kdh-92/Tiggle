package com.side.tiggle.global.exception.error

import org.springframework.http.HttpStatus

/**
 * 에러 코드 인터페이스
 * 각 에러 항목에 대한 HTTP 상태, 에러 번호, 메시지를 제공한다.
 *
 * T[DD][CCC]
 * T: Tiggle 프로젝트 식별자
 * DD: 도메인 코드 (10:Member, 20:Category, 30:Comment, 40:Reaction, 50:Tag, 60:Transaction, 70:Notification, 90:Global)
 * CCC: 세부 오류 순번 (001~999)
 *
 * @author 양병학
 * @since 2025-06-28
 */
interface ErrorCode {

    /**
     * HTTP 상태 반환
     * @return HTTP 상태
     */
    fun httpStatus(): HttpStatus

    /**
     * 에러 코드 번호 반환
     * @return 에러 코드 번호
     */
    fun codeNumber(): Int

    /**
     * 에러 메시지 반환
     * @return 에러 메시지
     */
    fun message(): String
}