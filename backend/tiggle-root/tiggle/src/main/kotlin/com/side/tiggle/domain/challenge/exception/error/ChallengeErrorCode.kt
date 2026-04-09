package com.side.tiggle.domain.challenge.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 챌린지(Challenge) 도메인 에러 코드 정의
 *
 * DD=85 (Challenge 도메인)
 */
enum class ChallengeErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {

    // 조회 관련 오류 (85001~85010)
    CHALLENGE_NOT_FOUND(HttpStatus.NOT_FOUND, 85001, "챌린지를 찾을 수 없습니다"),

    // 생성 관련 오류 (85011~85020)
    ACTIVE_CHALLENGE_EXISTS(HttpStatus.CONFLICT, 85011, "이미 진행 중인 챌린지가 있습니다"),
    INVALID_DATE_RANGE(HttpStatus.BAD_REQUEST, 85012, "종료일은 시작일 이후여야 합니다"),

    // 권한 관련 오류 (85021~85030)
    CHALLENGE_ACCESS_DENIED(HttpStatus.FORBIDDEN, 85021, "해당 챌린지에 대한 권한이 없습니다"),

    // 상태 관련 오류 (85031~85040)
    CHALLENGE_NOT_ACTIVE(HttpStatus.BAD_REQUEST, 85031, "활성 상태의 챌린지가 아닙니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}
