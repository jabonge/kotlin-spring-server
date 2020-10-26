package com.daly.server.common.exception



import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception


@ControllerAdvice
class GlobalExceptionController: ResponseEntityExceptionHandler() {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<CustomError> {
        return ResponseEntity(exception.toCustomError(),exception.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<CustomError> {
        val customError = CustomError(status = HttpStatus.INTERNAL_SERVER_ERROR, message = exception.message)
        return ResponseEntity(customError,customError.status)
    }

}