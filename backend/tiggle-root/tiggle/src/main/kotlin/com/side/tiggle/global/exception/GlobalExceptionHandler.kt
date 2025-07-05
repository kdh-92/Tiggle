package com.side.tiggle.global.exception

import com.side.tiggle.global.common.ApiResponse
import com.side.tiggle.global.exception.error.GlobalErrorCode
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import java.time.LocalDateTime
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException

/**
 * 전역 예외 처리 클래스
 * 컨트롤러에서 발생한 예외를 공통적으로 처리한다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(this::class.java)
    
    /**
     * ConstraintViolationException 처리
     * Path Variable, Request Parameter 유효성 검사 실패 시 처리
     */
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        val errorMessage = ex.constraintViolations
            .joinToString(", ") { "${it.propertyPath}: ${it.message}" }
        return buildErrorResponse(
            HttpStatus.BAD_REQUEST,
            400,
            errorMessage,
            ex,
            request
        )
    }

    /**
     * CustomException 처리
     * ErrorCode 인터페이스 기반으로 확장 가능한 방식으로 처리한다.
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(
        ex: CustomException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        val errorCode = ex.getErrorCode()
        return buildErrorResponse(
            errorCode.httpStatus(),
            errorCode.codeNumber(),
            errorCode.message(),
            ex,
            request
        )
    }

    /**
     * IllegalArgumentException 처리
     * 잘못된 파라미터에 대한 예외 응답 처리
     */
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(
        ex: IllegalArgumentException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        return buildErrorResponse(
            GlobalErrorCode.ILLEGAL_ARGUMENT.httpStatus(),
            GlobalErrorCode.ILLEGAL_ARGUMENT.codeNumber(),
            ex.message ?: GlobalErrorCode.ILLEGAL_ARGUMENT.message(),
            ex,
            request
        )
    }

    /**
     * MethodArgumentNotValidException 처리
     * 유효성 검사 실패에 대한 응답 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        val errorMessage = getValidationErrorMessage(ex)
        return buildErrorResponse(
            GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID.httpStatus(),
            GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID.codeNumber(),
            errorMessage,
            ex,
            request
        )
    }

    /**
     * BindException 처리
     * 폼 바인딩 유효성 실패 시 처리
     */
    @ExceptionHandler(BindException::class)
    fun handleBindException(
        ex: BindException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        val errorMessage = getValidationErrorMessage(ex)
        return buildErrorResponse(
            GlobalErrorCode.BIND_EXCEPTION.httpStatus(),
            GlobalErrorCode.BIND_EXCEPTION.codeNumber(),
            errorMessage,
            ex,
            request
        )
    }

    /**
     * NoHandlerFoundException 처리
     * 존재하지 않는 URL 경로 요청 시 처리
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        return buildErrorResponse(
            GlobalErrorCode.NOT_FOUND_ENDPOINT.httpStatus(),
            GlobalErrorCode.NOT_FOUND_ENDPOINT.codeNumber(),
            GlobalErrorCode.NOT_FOUND_ENDPOINT.message(),
            ex,
            request
        )
    }

    /**
     * 기타 모든 예외 처리
     * 정의되지 않은 예외는 내부 서버 오류로 응답
     */
    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        return buildErrorResponse(
            GlobalErrorCode.INTERNAL_SERVER_ERROR.httpStatus(),
            GlobalErrorCode.INTERNAL_SERVER_ERROR.codeNumber(),
            GlobalErrorCode.INTERNAL_SERVER_ERROR.message(),
            ex,
            request
        )
    }

    /**
     * 공통 에러 응답 생성 메서드
     * 예외 로깅 후 ApiResponse.failure 통해 표준화된 에러 응답을 생성한다.
     */
    private fun buildErrorResponse(
        status: HttpStatus,
        errorCode: Int,
        message: String,
        ex: Throwable,
        request: HttpServletRequest
    ): ResponseEntity<ApiResponse<Void>> {
        logger.error("[{} - {}] {}: {}", LocalDateTime.now(), request.requestURI, ex.javaClass.simpleName, ex.message, ex)

        val apiResponse = ApiResponse.failure(errorCode.toString(), message)

        return ResponseEntity(apiResponse, status)
    }

    /**
     * BindingResult 분석 후 필드별 오류 메시지 조합
     */
    private fun getValidationErrorMessage(ex: BindException): String {
        return ex.bindingResult.fieldErrors
            .joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
    }
}
