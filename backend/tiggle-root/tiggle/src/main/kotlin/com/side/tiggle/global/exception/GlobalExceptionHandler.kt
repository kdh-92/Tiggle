package com.side.tiggle.global.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException

@RestControllerAdvice
class GlobalExceptionHandler {
    val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception, request: HttpServletRequest): ErrorResponse {
        logger.error(e.message, e)
        return ErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request)
    }

    @ExceptionHandler(value = [IllegalArgumentException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIllegalArgumentException(e: IllegalArgumentException, request: HttpServletRequest): ErrorResponse {
        logger.error(e.message, e)
        return ErrorResponse(e, HttpStatus.BAD_REQUEST, request)
    }

    @ExceptionHandler(value = [NotFoundException::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFoundException(e: NotFoundException, request: HttpServletRequest): ErrorResponse {
        logger.error(e.message, e)
        return ErrorResponse(e, HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(value = [NotAuthorizedException::class])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleNotAuthorizedException(e: NotAuthorizedException, request: HttpServletRequest): ErrorResponse {
        logger.error(e.message, e)
        return ErrorResponse(e, HttpStatus.UNAUTHORIZED, request)
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(
        e: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ErrorResponse {
        logger.error("Validation failed: ${getValidationErrorMessage(e)}", e)
        return ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "ValidationError",
            message = getValidationErrorMessage(e),
            trace = staceTraceToString(e.stackTrace),
            path = request.requestURI
        )
    }

    @ExceptionHandler(value = [BindException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(
        e: BindException,
        request: HttpServletRequest
    ): ErrorResponse {
        logger.error("Bind validation failed: ${getValidationErrorMessage(e)}", e)
        return ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "BindValidationError",
            message = getValidationErrorMessage(e),
            trace = staceTraceToString(e.stackTrace),
            path = request.requestURI
        )
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(
        e: ConstraintViolationException,
        request: HttpServletRequest
    ): ErrorResponse {
        val errorMessage = e.constraintViolations.joinToString(", ") { violation ->
            "${violation.propertyPath}: ${violation.message}"
        }
        logger.error("Constraint validation failed: $errorMessage", e)
        return ErrorResponse(
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = "ConstraintViolationError",
            message = errorMessage,
            trace = staceTraceToString(e.stackTrace),
            path = request.requestURI
        )
    }

    private fun getValidationErrorMessage(e: BindException): String {
        return e.bindingResult.fieldErrors.joinToString(", ") { fieldError ->
            "${fieldError.field}: ${fieldError.defaultMessage}"
        }
    }

    data class ErrorResponse(
        val timestamp: LocalDateTime,
        val status: Int,
        val error: String,
        val message: String?,
        val trace: String?,
        val path: String
    ) {
        constructor(
            e: Throwable,
            status: HttpStatus,
            request: HttpServletRequest
        ): this(
            timestamp = LocalDateTime.now(),
            status = status.value(),
            error = e.javaClass.simpleName,
            message = e.message,
            trace = staceTraceToString(e.stackTrace),
            path = request.contextPath
        )
    }

    companion object {
        fun staceTraceToString(
            stackTrace: Array<StackTraceElement>
        ): String {
            return stackTrace.map {
                it.toString()
            }.joinToString("\n")
        }
    }
}