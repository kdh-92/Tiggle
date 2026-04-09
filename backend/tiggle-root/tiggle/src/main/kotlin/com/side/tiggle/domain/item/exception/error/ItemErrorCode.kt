package com.side.tiggle.domain.item.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 아이템(Item) 도메인 에러 코드 정의
 *
 * DD=85 (Item 도메인)
 */
enum class ItemErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    // 조회 관련 오류 (85001~85010)
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, 85001, "아이템을 찾을 수 없습니다"),

    // 장착 관련 오류 (85011~85020)
    ITEM_NOT_OWNED(HttpStatus.BAD_REQUEST, 85011, "보유하지 않은 아이템입니다"),
    ITEM_SLOT_MISMATCH(HttpStatus.BAD_REQUEST, 85012, "아이템 슬롯이 일치하지 않습니다"),
    ITEM_ALREADY_OWNED(HttpStatus.CONFLICT, 85013, "이미 보유한 아이템입니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}
