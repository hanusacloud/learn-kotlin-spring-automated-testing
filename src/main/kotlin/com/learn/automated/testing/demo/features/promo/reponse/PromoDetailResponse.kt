package com.learn.automated.testing.demo.features.promo.reponse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.response.BookResponse
import com.learn.automated.testing.demo.features.promo.models.Promo
import com.learn.automated.testing.demo.shared.responses.BaseResponse

class PromoDetailResponse : BaseResponse {

    @JsonProperty("promo")
    private val promo: PromoResponse?

    @JsonCreator
    constructor(
            @JsonProperty("status") status: Boolean,
            @JsonProperty("message") message: String,
            @JsonProperty("promo") promo: PromoResponse?
    ) : super (status, message) {
        this.promo = promo
    }

    constructor(
            status: Boolean,
            message: String,
            promo: Promo
    ) : super (status, message) {
        this.promo = PromoResponse(
                promo.id ?: 0,
                BookResponse(promo.book),
                promo.startDate,
                promo.endDate
        )
    }

    fun getPromo(): PromoResponse? = promo

}