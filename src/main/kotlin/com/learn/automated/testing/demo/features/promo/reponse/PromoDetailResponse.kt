package com.learn.automated.testing.demo.features.promo.reponse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.response.BookItem
import com.learn.automated.testing.demo.features.promo.models.Promo
import com.learn.automated.testing.demo.shared.responses.BaseResponse

class PromoDetailResponse : BaseResponse {

    @JsonProperty("promo")
    private val promo: PromoItem?

    @JsonCreator
    constructor(
            @JsonProperty("status") status: Boolean,
            @JsonProperty("message") message: String,
            @JsonProperty("promo") promo: PromoItem
    ) : super (status, message) {
        this.promo = promo
    }

    constructor(
            status: Boolean,
            message: String,
            promo: Promo
    ) : super (status, message) {
        this.promo = PromoItem(
                promo.id ?: 0,
                BookItem(promo.book),
                promo.startDate,
                promo.endDate
        )
    }

    constructor(message: String) : super (false, message) {
        this.promo = null
    }

    fun getPromo(): PromoItem? = promo

}