package com.learn.automated.testing.demo.integrations.book

import com.learn.automated.testing.demo.DemoApplication
import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.controllers.BookController
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookDetailResponse
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ContextConfiguration

@SpringBootTest(
        classes = [BookController::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = [DemoApplication::class])
class BookControllerIT (
        @Autowired val bookRepository: BookRepository,
        @Autowired val restTemplate: TestRestTemplate,
        @LocalServerPort val port: Int
) {

    lateinit var book: Book

    @BeforeEach
    fun setUp() {
        book = bookRepository.save(Book(1, "Test Title", 500))
        bookRepository.save(Book(2, "Test Title 2", 590))
    }

    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        bookRepository.flush()
    }

    fun getUrl(path: String): String = "http://localhost:${port}" + path

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
                getUrl("/api/book"),
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
                getUrl("/api/book/${id}"),
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
                "/api/books",
                BookResponseList::class.java
        )
        assertThat(response.body?.getItems()?.size).isEqualTo(2)
    }

    @Test
    fun shouldBeAbleToUpdateABook() {
        val response: ResponseEntity<BookDetailResponse> = sendUpdateRequest(
                book.id,
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