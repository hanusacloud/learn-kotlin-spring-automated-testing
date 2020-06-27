package com.learn.automated.testing.demo.integrations.book

import com.fasterxml.jackson.databind.ObjectMapper
import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.controllers.BookController
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookDetailResponse
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import com.learn.automated.testing.demo.integrations.BaseIntegration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*

@SpringBootTest(
        classes = [BookController::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIT : BaseIntegration() {

    @Autowired
    protected lateinit var bookRepository: BookRepository

    lateinit var book: Book

    @BeforeEach
    fun setUp() {
        book = bookRepository.save(Book(title = "Test Title", totalPage =  500))
        bookRepository.save(Book(title = "Test Title 2", totalPage = 590))
    }

    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        bookRepository.flush()
    }

    private fun <T> sendRequest(path: String, type: Class<T>) : ResponseEntity<T> {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val entity: HttpEntity<String> = HttpEntity(null, httpHeaders)
        return restTemplate.exchange(
                getUrl(path),
                HttpMethod.GET,
                entity,
                type
        )
    }

    fun <T> sendCreateRequest(
            bookRequest: BookRequest,
            type: Class<T>
    ): ResponseEntity<T> {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val entity: HttpEntity<BookRequest> = HttpEntity(bookRequest, httpHeaders)
        return restTemplate.exchange(
                getUrl("/api/book/create"),
                HttpMethod.POST,
                entity,
                type
        )
    }

    fun sendUpdateRequest(
            id: Long ,
            bookRequest: BookRequest
    ): ResponseEntity<BookDetailResponse> {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val entity: HttpEntity<BookRequest> = HttpEntity(bookRequest, httpHeaders)
        return restTemplate.exchange(
                getUrl("/api/book/update/${id}"),
                HttpMethod.PUT,
                entity,
                BookDetailResponse::class.java
        )
    }

    @Test
    fun shouldBeAbleToGetDetailById() {
        val response: ResponseEntity<BookDetailResponse> = sendRequest(
                "/api/book/${book.id}",
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getBook()?.getTitle()).isEqualTo("Test Title")
    }

    @Test
    fun shouldNotBeAbleToGetDetailById() {
        val response: ResponseEntity<BookDetailResponse> = sendRequest(
                "/api/book/99999999",
                BookDetailResponse::class.java
        )
        assertThat(response.body?.message).isEqualTo("Book not found!")
    }

    @Test
    fun shouldBeAbleToReturnBookList() {
        val response: ResponseEntity<BookResponseList> = sendRequest(
                "/api/book/list",
                BookResponseList::class.java
        )
        assertThat(response.body?.getItems()?.size).isEqualTo(2)
    }

    @Test
    fun shouldBeAbleToUpdateABook() {
        val response: ResponseEntity<BookDetailResponse> = sendUpdateRequest(
                book.id!!,
                BookRequest("Test Updated Title", 400)
        )
        assertThat(response.body?.getBook()?.getTitle()).isEqualTo("Test Updated Title")
    }

    @Test
    fun shouldNotBeAbleToUpdateABook() {
        val response: ResponseEntity<BookDetailResponse> = sendUpdateRequest(
                9999999,
                BookRequest("Test Updated Title", 400)
        )
        println(ObjectMapper().writeValueAsString(response))
        assertThat(response.body?.message).isEqualTo("Book not found!")
    }

    @Test
    fun shouldBeAbleToCreateABook() {
        val response: ResponseEntity<BookDetailResponse> = sendCreateRequest(
                BookRequest("Test Create Title", 200),
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getBook()?.getTitle()).isEqualTo("Test Create Title")
    }

}