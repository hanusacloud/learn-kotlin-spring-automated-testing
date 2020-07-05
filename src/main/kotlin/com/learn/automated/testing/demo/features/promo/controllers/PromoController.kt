package com.learn.automated.testing.demo.features.promo.controllers

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.promo.exceptions.PromoException
import com.learn.automated.testing.demo.features.promo.models.Promo
import com.learn.automated.testing.demo.features.promo.reponse.PromoDetailResponse
import com.learn.automated.testing.demo.features.promo.repositories.PromoRepository
import com.learn.automated.testing.demo.features.promo.requests.PromoRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/promo")
class PromoController (
        @Autowired val repository: PromoRepository,
        @Autowired val bookRepository: BookRepository
) {

    @RequestMapping(method = [RequestMethod.POST])
    @Throws(BookException::class, PromoException::class)
    fun create(
            @Valid @RequestBody promoRequest: PromoRequest
    ): ResponseEntity<PromoDetailResponse> {
        val book: Optional<Book> = bookRepository.findById(promoRequest.bookId!!)
        if (!book.isPresent) {
            throw BookException.notFound()
        }
        val promoPrice = promoRequest.promoPrice!!
        if (promoPrice >= book.get().getPrice()) {
            throw PromoException.promoPriceExceedsOriginal()
        }
        val promo = Promo(
                book = book.get(),
                startDate = promoRequest.startDate!!,
                endDate = promoRequest.endDate!!,
                promoPrice = promoPrice
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