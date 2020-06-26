package com.learn.automated.testing.demo.features.promo.controllers

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.promo.models.Promo
import com.learn.automated.testing.demo.features.promo.reponse.PromoDetailResponse
import com.learn.automated.testing.demo.features.promo.repositories.PromoRepository
import com.learn.automated.testing.demo.features.promo.requests.PromoRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class PromoController (
        @Autowired val repository: PromoRepository,
        @Autowired val bookRepository: BookRepository
) {

    @PostMapping("/api/promo")
    fun create(
            @RequestBody promoRequest: PromoRequest
    ): ResponseEntity<PromoDetailResponse> {
        val book: Optional<Book> = bookRepository.findById(promoRequest.bookId)
        if (!book.isPresent) {
            return ResponseEntity(
                    PromoDetailResponse("Book not found!"),
                    HttpStatus.OK
            )
        }
        val promo = Promo(
                book = book.get(),
                startDate = promoRequest.startDate,
                endDate = promoRequest.endDate
        )
        return ResponseEntity(
                PromoDetailResponse(
                        true,
                        "success",
                        repository.save(promo)
                ),
                HttpStatus.OK
        )
    }

}