package com.learn.automated.testing.demo.features.book.controllers

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookDetailResponse
import com.learn.automated.testing.demo.features.book.response.BookItem
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@Validated
class BookController (
        val repository: BookRepository
) {

    @GetMapping("/api/book/{id}")
    fun getDetail(@PathVariable(name = "id") id: Long): ResponseEntity<BookDetailResponse> {
        val book: Optional<Book> = repository.findById(id)
        if (!book.isPresent) {
            return ResponseEntity(
                    BookDetailResponse("Book not found!"),
                    HttpStatus.OK
            )
        }
        return ResponseEntity(
                BookDetailResponse(true, "success", book.get()),
                HttpStatus.OK
        )
    }

    @GetMapping("/api/books")
    fun getList(): ResponseEntity<BookResponseList> {
        val book: List<Book> = repository.findAll()
        return ResponseEntity(
                BookResponseList(
                        true,
                        "success",
                        book.map { e -> BookItem(e) }.toList()),
                HttpStatus.OK
        )
    }

    @PostMapping("/api/book")
    fun create(
            @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        val book = Book(
                0,
                bookRequest.title,
                bookRequest.totalPage
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

    @PutMapping("/api/book/{id}")
    fun update(
            @PathVariable(name = "id") id: Long,
            @RequestBody bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        val bookOptional: Optional<Book> = repository.findById(id)
        if (!bookOptional.isPresent) {
            return ResponseEntity(
                    BookDetailResponse("Book not found!"),
                    HttpStatus.OK
            )
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