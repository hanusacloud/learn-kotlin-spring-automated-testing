package com.learn.automated.testing.demo.features.promo.reponse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.common.features.book.BookResponse
import java.util.*

data class PromoResponse @JsonCreator constructor (
        @JsonProperty("id") val id: Long,
        @JsonProperty("promo_price") val promoPrice: Long,
        @JsonProperty("book") val book: BookResponse,
        @JsonProperty("startDate") val startDate: Date,
        @JsonProperty("endDate") val endDate: Date
)