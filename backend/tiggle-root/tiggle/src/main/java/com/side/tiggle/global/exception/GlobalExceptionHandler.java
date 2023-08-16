package com.side.tiggle.global.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e, HttpServletRequest request) {
        return new ErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request){
        return new ErrorResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        return new ErrorResponse(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = NotAuthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleNotAuthorizedException(NotAuthorizedException e, HttpServletRequest request) {
        return new ErrorResponse(e, HttpStatus.UNAUTHORIZED, request);
    }

    private static String stackTraceToString(StackTraceElement[] stackTrace) {
        return Arrays.stream(stackTrace)
                .map(StackTraceElement::toString)
                .collect(Collectors.joining("\n"));
    }

    @Getter
    @Setter
    private static class ErrorResponse {
        private final LocalDateTime timestamp;
        private final int status;
        private final String error;
        private final String message;
        private final String trace;
        private final String path;

        ErrorResponse(Throwable e, HttpStatus status, HttpServletRequest request){
            this.timestamp = LocalDateTime.now();
            this.status = status.value();
            this.error = e.getClass().getSimpleName();
            this.message = e.getMessage();
            this.trace = stackTraceToString(e.getStackTrace());
            this.path = request.getContextPath();
        }
    }
}
