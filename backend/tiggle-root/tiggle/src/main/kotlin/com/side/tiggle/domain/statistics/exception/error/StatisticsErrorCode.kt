package com.side.tiggle.domain.statistics.exception.error

import com.side.tiggle.global.exception.error.ErrorCode
import org.springframework.http.HttpStatus

enum class StatisticsErrorCode(
    private val status: HttpStatus,
    private val code: Int,
    private val msg: String
) : ErrorCode {

    SNAPSHOT_NOT_FOUND(HttpStatus.NOT_FOUND, 80001, "통계 스냅샷을 찾을 수 없습니다"),
    INVALID_WEEK_OFFSET(HttpStatus.BAD_REQUEST, 80002, "유효하지 않은 주 오프셋입니다"),
    STATISTICS_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 80003, "통계 생성에 실패했습니다");

    override fun httpStatus(): HttpStatus = status
    override fun codeNumber(): Int = code
    override fun message(): String = msg
}
