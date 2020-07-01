package com.learn.automated.testing.demo.features.book.controllers

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookDetailResponse
import com.learn.automated.testing.demo.features.book.response.BookResponse
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@Validated
@RequestMapping("/api/book")
class BookController (
        val repository: BookRepository
) {

    @GetMapping("/{id}")
    @Throws(BookException::class)
    fun getDetail(@PathVariable(name = "id") id: Long): ResponseEntity<BookDetailResponse> {
        val book: Optional<Book> = repository.findById(id)
        if (!book.isPresent) {
            throw BookException.notFound()
        }
        return ResponseEntity(
                BookDetailResponse(true, "success", book.get()),
                HttpStatus.OK
        )
    }

    @GetMapping("/list")
    fun getList(): ResponseEntity<BookResponseList> {
        val book: List<Book> = repository.findAll()
        return ResponseEntity(
                BookResponseList(
                        true,
                        "success",
                        book.map { e -> BookResponse(e) }.toList()),
                HttpStatus.OK
        )
    }

    @PostMapping("/create")
    fun create(
            @Valid @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        val book = Book(
                title = bookRequest.title,
                totalPage = bookRequest.totalPage,
                price = bookRequest.price
        )
        return ResponseEntity(
                BookDetailResponse(
                        true,
                        "success",
                        repository.save(book)
                ),
                HttpStatus.OK
        )
    }

    @PutMapping("/update/{id}")
    @Throws(BookException::class)
    fun update(
            @PathVariable(name = "id") id: Long,
            @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        val bookOptional: Optional<Book> = repository.findById(id)
        if (!bookOptional.isPresent) {
            throw BookException.notFound()
        }
        val book = bookOptional.get()
        book.title = bookRequest.title
        book.totalPage = bookRequest.totalPage
        return ResponseEntity(
                BookDetailResponse(true, "success", repository.save(book)),
                HttpStatus.OK
        )
    }

}