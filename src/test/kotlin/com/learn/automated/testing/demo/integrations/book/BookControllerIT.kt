package com.learn.automated.testing.demo.integrations.book

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.controllers.BookController
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookDetailResponse
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import com.learn.automated.testing.demo.features.category.models.Category
import com.learn.automated.testing.demo.features.category.repositories.CategoryRepository
import com.learn.automated.testing.demo.integrations.BaseIntegration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.*

@SpringBootTest(
        classes = [BookController::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerIT : BaseIntegration() {

    @Autowired
    protected lateinit var bookRepository: BookRepository
    @Autowired
    protected lateinit var categoryRepository: CategoryRepository

    lateinit var book: Book
    lateinit var category: Category

    @BeforeEach
    fun setUp() {
        category = categoryRepository.save(Category(name = "Horror"))
        book = bookRepository.save(
                Book(
                        title = "Test Title",
                        totalPage =  500,
                        price = 100,
                        category = category
                )
        )
        bookRepository.save(
                Book(
                        title = "Test Title 2",
                        totalPage = 590,
                        price = 200,
                        category = category
                )
        )
    }

    @AfterEach
    fun tearDown() {
        bookRepository.deleteAll()
        bookRepository.flush()
        categoryRepository.deleteAll()
        categoryRepository.flush()
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

    fun <T, P> sendCreateRequest(
            bookRequest: P,
            type: Class<T>
    ): ResponseEntity<T> {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val entity: HttpEntity<P> = HttpEntity(bookRequest, httpHeaders)
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
                "/api/book/${book.getId()}",
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getBook()?.getTitle())
                .isEqualTo("Test Title")
    }

    @Test
    fun shouldBeAbleToGetDetailByIdAndValidateCategory() {
        val response: ResponseEntity<BookDetailResponse> = sendRequest(
                "/api/book/${book.getId()}",
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getBook()?.getCategory()?.getName())
                .isEqualTo("Horror")
    }

    @Test
    fun shouldNotBeAbleToGetDetailByIdAndReturnBookNotFoundMessage() {
        val response: ResponseEntity<BookDetailResponse> = sendRequest(
                "/api/book/99999999",
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getMessage())
                .isEqualTo("Book not found!")
    }

    @Test
    fun shouldBeAbleToReturnBookList() {
        val response: ResponseEntity<BookResponseList> = sendRequest(
                "/api/book/list",
                BookResponseList::class.java
        )
        assertThat(response.body?.getItems()?.size)
                .isEqualTo(2)
    }

    @Test
    fun shouldBeAbleToUpdateABook() {
        val response: ResponseEntity<BookDetailResponse> = sendUpdateRequest(
                book.getId()!!,
                BookRequest(
                        "Test Updated Title",
                        400,
                        300,
                        category.getId()
                )
        )
        assertThat(response.body?.getBook()?.getTitle())
                .isEqualTo("Test Updated Title")
    }

    @Test
    fun shouldBeAbleToUpdateThePriceOfaBook() {
        val response: ResponseEntity<BookDetailResponse> = sendUpdateRequest(
                book.getId()!!,
                BookRequest(
                        "Test Updated Title",
                        400,
                        3000,
                        category.getId()
                )
        )
        assertThat(response.body?.getBook()?.getPrice())
                .isEqualTo(3000)
    }

    @Test
    fun shouldNotBeAbleToUpdateABookAndReturnBookNotFoundMessage() {
        val response: ResponseEntity<BookDetailResponse> = sendUpdateRequest(
                9999999,
                BookRequest(
                        "Test Updated Title",
                        400,
                        100,
                        category.getId()
                )
        )
        assertThat(response.body?.getMessage())
                .isEqualTo("Book not found!")
    }

    @Test
    fun shouldBeAbleToCreateABook() {
        val response: ResponseEntity<BookDetailResponse> = sendCreateRequest(
                BookRequest(
                        "Test Create Title",
                        200,
                        60,
                        category.getId()
                ),
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getBook()?.getTitle())
                .isEqualTo("Test Create Title")
    }

    @Test
    fun shouldBeAbleToCreateABookCategoryNotFound() {
        val response: ResponseEntity<BookDetailResponse> = sendCreateRequest(
                BookRequest(
                        "Test Create Title",
                        200,
                        60,
                        99999
                ),
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getMessage())
                .isEqualTo("Category not found!")
    }

    @ParameterizedTest
    @CsvFileSource(
            resources = ["/book/book_validation_expectation.csv"],
            delimiter = ';',
            numLinesToSkip = 1
    )
    fun shouldBeAbleToTestFormRequestValidation(jsonRequest: String, expected: String) {
        val response: ResponseEntity<BookDetailResponse> = sendCreateRequest(
                jsonRequest,
                BookDetailResponse::class.java
        )
        assertThat(response.body?.getErrorMessages())
                .containsAnyElementsOf(
                        arrayListOf(
                                expected
                        )
                )
    }

}