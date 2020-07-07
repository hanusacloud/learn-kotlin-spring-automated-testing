package com.learn.automated.testing.demo.integrations.promo

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.category.models.Category
import com.learn.automated.testing.demo.features.category.repositories.CategoryRepository
import com.learn.automated.testing.demo.features.promo.controllers.PromoController
import com.learn.automated.testing.demo.features.promo.reponse.PromoDetailResponse
import com.learn.automated.testing.demo.features.promo.repositories.PromoRepository
import com.learn.automated.testing.demo.features.promo.requests.PromoRequest
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
import java.util.*

@SpringBootTest(
        classes = [PromoController::class],
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PromoControllerIT : BaseIntegration() {

    @Autowired
    private lateinit var repository: PromoRepository
    @Autowired
    private lateinit var bookRepository: BookRepository
    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    lateinit var book: Book

    @BeforeEach
    fun setUp() {
        val category: Category = categoryRepository.save(
                Category(name = "Sport")
        )
        book = bookRepository.save(
                Book(
                        title = "Test Book For Promo",
                        totalPage = 500,
                        price = 300,
                        category = category
                )
        )
    }

    @AfterEach
    fun tearDown() {
        repository.deleteAll()
        repository.flush()
        bookRepository.deleteAll()
        bookRepository.flush()
        categoryRepository.deleteAll()
        categoryRepository.flush()
    }

    fun <T, P> sendCreateRequest(
            promoRequest: P,
            type: Class<T>
    ): ResponseEntity<T> {
        val httpHeaders = HttpHeaders()
        httpHeaders.contentType = MediaType.APPLICATION_JSON
        val entity: HttpEntity<P> = HttpEntity(promoRequest, httpHeaders)
        return restTemplate.exchange(
                getUrl("/api/promo"),
                HttpMethod.POST,
                entity,
                type
        )
    }

    fun sendCreateRequest(bookId: Long, promoPrice: Long) : ResponseEntity<PromoDetailResponse> {
        return sendCreateRequest(
                PromoRequest(
                        bookId = bookId,
                        startDate = Date(),
                        endDate = Date(),
                        promoPrice = promoPrice
                ),
                PromoDetailResponse::class.java
        )
    }

    @Test
    fun shouldBeAbleToCreatePromo() {
        val response = sendCreateRequest(book.getId()!!, 290)
        assertThat(response.body?.getPromo()?.book?.getTitle())
                .isEqualTo("Test Book For Promo")
    }

    @Test
    fun shouldBeAbleToCreatePromoAndReturnPromoPrice() {
        val response = sendCreateRequest(book.getId()!!, 290)
        assertThat(response.body?.getPromo()?.promoPrice)
                .isEqualTo(290)
    }

    @Test
    fun shouldNotBeAbleToCreatePromoCausedByPromoPriceHigherThanOriginal() {
        val response = sendCreateRequest(book.getId()!!, 350)
        assertThat(response.body?.getMessage())
                .isEqualTo("Promo price can't be higher than original price!")
    }

    @Test
    fun shouldNotBeAbleToCreatePromoOnNonExistingBook() {
        val response = sendCreateRequest(99999999, 20)
        assertThat(response.body?.getMessage())
                .isEqualTo("Book not found!")
    }

    @ParameterizedTest
    @CsvFileSource(
            resources = ["/promo/promo_validation_expectation.csv"],
            delimiter = ';',
            numLinesToSkip = 1
    )
    fun shouldNotBeAbleToCreatePromoWhenMissingValue(
            jsonRequest: String,
            expected: String
    ) {
        val response = sendCreateRequest(jsonRequest, PromoDetailResponse::class.java)
        assertThat(response.body?.getErrorMessages())
                .containsAnyElementsOf(
                        arrayListOf(
                                expected
                        )
                )
    }

}