package com.learn.automated.testing.demo.features.promo.services

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.promo.exceptions.PromoException
import com.learn.automated.testing.demo.features.promo.models.Promo
import com.learn.automated.testing.demo.features.promo.reponse.PromoDetailResponse
import com.learn.automated.testing.demo.features.promo.repositories.PromoRepository
import com.learn.automated.testing.demo.features.promo.requests.PromoRequest
import org.springframework.stereotype.Service
import java.util.*

@Service
class PromoServiceImpl constructor(
        val repository: PromoRepository,
        val bookRepository: BookRepository
) : PromoService {

    override fun create(promoRequest: PromoRequest): PromoDetailResponse {
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
        return PromoDetailResponse(
                true,
                "success",
                repository.save(promo)
        )
    }

}