package com.learn.automated.testing.demo.features.promo.reponse

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.response.BookItem
import java.util.*

data class PromoItem @JsonCreator constructor (
        @JsonProperty("id") val id: Long,
        @JsonProperty("book") val book: BookItem,
        @JsonProperty("startDate") val startDate: Date,
        @JsonProperty("endDate") val endDate: Date
)