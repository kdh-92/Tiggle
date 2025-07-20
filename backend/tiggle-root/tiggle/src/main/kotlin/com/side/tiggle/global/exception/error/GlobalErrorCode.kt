
package com.side.tiggle.global.exception.error

import org.springframework.http.HttpStatus

/**
 * 전역 에러 코드 정의
 * 인증, 권한, 공통 시스템 오류 등을 포함
 */
enum class GlobalErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {

    // 인증 관련 오류
    NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, 90001, "인증되지 않은 사용자입니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 90002, "유효하지 않은 토큰입니다"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, 90003, "토큰이 만료되었습니다"),
    TOKEN_PARSE_FAILED(HttpStatus.UNAUTHORIZED, 90004, "토큰 파싱에 실패했습니다"),
    OAUTH2_AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, 90005, "OAuth2 인증에 실패했습니다"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 90006, "유효하지 않은 refresh token입니다"),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 90007, "refresh token이 만료되었습니다"),

    // 요청 관련 오류
    ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, 90011, "잘못된 파라미터입니다"),
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, 90012, "유효성 검사에 실패했습니다"),
    BIND_EXCEPTION(HttpStatus.BAD_REQUEST, 90013, "요청 바인딩에 실패했습니다"),
    NOT_FOUND_ENDPOINT(HttpStatus.NOT_FOUND, 90014, "요청하신 경로를 찾을 수 없습니다"),

    // 서버 관련 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 90020, "서버 내부 오류가 발생했습니다"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 90021, "데이터베이스 오류가 발생했습니다"),
    EXTERNAL_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 90022, "외부 API 호출 중 오류가 발생했습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}