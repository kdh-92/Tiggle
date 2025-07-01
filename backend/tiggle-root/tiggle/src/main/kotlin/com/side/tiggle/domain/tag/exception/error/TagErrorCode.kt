package com.side.tiggle.domain.tag.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 태그(Tag) 도메인 에러 코드 정의
 * 태그 관련 예외 상황들에 대한 코드 및 메시지 제공
 */
enum class TagErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    TAG_NOT_FOUND(HttpStatus.NOT_FOUND, 50001, "태그를 찾을 수 없습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}