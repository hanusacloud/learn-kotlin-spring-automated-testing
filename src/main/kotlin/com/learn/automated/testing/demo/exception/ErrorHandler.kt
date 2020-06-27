package com.learn.automated.testing.demo.exception

import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.shared.responses.BaseResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(BookException::class)
    fun handle(exception: BookException): ResponseEntity<BaseResponse> {
        return ResponseEntity(
                BaseResponse(
                        false,
                        exception.message ?: "Oops.. something happened"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

}