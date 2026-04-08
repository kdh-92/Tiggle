package com.side.tiggle.domain.character.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 캐릭터(Character) 도메인 에러 코드 정의
 *
 * DD=80 (Character 도메인)
 */
enum class CharacterErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    // 조회 관련 오류 (80001~80010)
    CHARACTER_NOT_FOUND(HttpStatus.NOT_FOUND, 80001, "캐릭터를 찾을 수 없습니다"),
    CATALOG_NOT_FOUND(HttpStatus.NOT_FOUND, 80002, "캐릭터 카탈로그를 찾을 수 없습니다"),

    // 상태 관련 오류 (80011~80020)
    CHARACTER_ALREADY_HATCHED(HttpStatus.BAD_REQUEST, 80011, "이미 부화된 캐릭터입니다"),
    CHARACTER_NOT_READY_TO_HATCH(HttpStatus.BAD_REQUEST, 80012, "아직 부화 조건을 충족하지 않았습니다"),

    // 회원 관련 오류 (80021~80030)
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 80021, "회원을 찾을 수 없습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}
