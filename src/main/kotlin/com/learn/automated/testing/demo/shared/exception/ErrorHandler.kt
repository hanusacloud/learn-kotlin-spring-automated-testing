package com.learn.automated.testing.demo.shared.exception

import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.category.exceptions.CategoryException
import com.learn.automated.testing.demo.shared.responses.BaseResponse
import com.learn.automated.testing.demo.shared.utils.Translation
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    private fun generateResponse(message: String?): ResponseEntity<BaseResponse> {
        return ResponseEntity(
                BaseResponse(
                        false,
                        message ?: "Oops.. something happened"
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    @ExceptionHandler(BookException::class)
    fun handle(exception: BookException): ResponseEntity<BaseResponse> {
        return generateResponse(exception.message)
    }

    @ExceptionHandler(CategoryException::class)
    fun handleCategory(exception: CategoryException): ResponseEntity<BaseResponse> {
        return generateResponse(exception.message)
    }

    override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        super.handleMethodArgumentNotValid(ex, headers, status, request)
        val errorMessages = ex.bindingResult.allErrors
                .filter { it.defaultMessage != null }
                .map { Translation.getMessage(it.defaultMessage!!) }
                .toList()
        return ResponseEntity(
                BaseResponse(
                        false,
                        "Contain errors!",
                        errorMessages
                ),
                HttpStatus.BAD_REQUEST
        )
    }

}