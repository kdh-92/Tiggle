package com.side.tiggle.global.common

/**
 * 공통 API 응답 포맷
 *
 * ## code 규칙
 * - S000 : 성공
 * - E100 : 서버 장애
 * - E101 : 로그인 실패
 *
 * @param success 요청 성공 여부 (true: 성공, false: 실패)
 * @param code 비즈니스/에러 코드 (예: S000, E100 등)
 * @param message 사용자 메시지
 * @param data 실제 응답 데이터 (nullable)
 */

data class ApiResponse<T>(
    val success: Boolean,
    val code: String,
    val message: String,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T?, code: String = "S000", message: String = "요청에 성공했습니다."): ApiResponse<T> =
            ApiResponse(true, code, message, data)

        fun failure(code: String, message: String): ApiResponse<Void> =
            ApiResponse(false, code, message, null)
    }
}
