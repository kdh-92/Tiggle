package com.side.tiggle.domain.comment.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

/**
 * 댓글(Comment) 도메인 에러 코드 정의
 * 댓글 관련 예외 상황들에 대한 코드 및 메시지 제공
 */
enum class CommentErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {
    // 조회 관련 오류 (30001~30010)
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 30001, "댓글을 찾을 수 없습니다"),

    // 권한 관련 오류 (30011~30020)
    COMMENT_ACCESS_DENIED(HttpStatus.FORBIDDEN, 30011, "해당 댓글에 대한 권한이 없습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}