package com.learn.automated.testing.demo.features.promo.controllers

import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.promo.exceptions.PromoException
import com.learn.automated.testing.demo.features.promo.reponse.PromoDetailResponse
import com.learn.automated.testing.demo.features.promo.requests.PromoRequest
import com.learn.automated.testing.demo.features.promo.services.PromoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/promo")
class PromoController (
        val service: PromoService
) {

    @RequestMapping(method = [RequestMethod.POST])
    @Throws(BookException::class, PromoException::class)
    fun create(
            @Valid @RequestBody promoRequest: PromoRequest
    ): ResponseEntity<PromoDetailResponse> {
        return ResponseEntity(
                service.create(promoRequest),
                HttpStatus.OK
        )
    }

}