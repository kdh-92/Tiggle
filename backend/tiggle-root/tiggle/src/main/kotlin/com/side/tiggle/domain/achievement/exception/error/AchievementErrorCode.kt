package com.side.tiggle.domain.achievement.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 업적(Achievement) 도메인 에러 코드 정의
 *
 * DD=86 (Achievement 도메인)
 */
enum class AchievementErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    // 조회 관련 오류 (86001~86010)
    ACHIEVEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 86001, "업적을 찾을 수 없습니다"),

    // 상태 관련 오류 (86011~86020)
    ACHIEVEMENT_ALREADY_ACHIEVED(HttpStatus.CONFLICT, 86011, "이미 달성한 업적입니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}
