package com.learn.automated.testing.demo.features.book.controllers

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import com.learn.automated.testing.demo.features.book.services.BookService
import com.learn.automated.testing.demo.features.category.exceptions.CategoryException
import com.learn.common.features.book.BookDetailResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("/api/book")
class BookController (
        val repository: BookRepository,
        val service: BookService
) {

    @GetMapping("/{id}")
    @Throws(BookException::class)
    fun getDetail(@PathVariable(name = "id") id: Long): ResponseEntity<BookDetailResponse> {
        return ResponseEntity(
                service.detail(id),
                HttpStatus.OK
        )
    }

    @GetMapping("/list")
    fun getList(): ResponseEntity<BookResponseList> {
        return ResponseEntity(
                service.getAll(),
                HttpStatus.OK
        )
    }

    @PostMapping("/create")
    @Throws(CategoryException::class)
    fun create(
            @Valid @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        return ResponseEntity(
                service.create(bookRequest),
                HttpStatus.OK
        )
    }

    @PutMapping("/update/{id}")
    @Throws(BookException::class)
    fun update(
            @PathVariable(name = "id") id: Long,
            @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        return ResponseEntity(
                service.update(id, bookRequest),
                HttpStatus.OK
        )
    }

}