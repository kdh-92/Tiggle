package com.side.tiggle.domain.category.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 카테고리(Category) 도메인 에러 코드 정의
 * 카테고리 관련 예외 상황들에 대한 코드 및 메시지 제공
 */
enum class CategoryErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    // 조회 관련 오류 (20001~20010)
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 20001, "카테고리를 찾을 수 없습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}