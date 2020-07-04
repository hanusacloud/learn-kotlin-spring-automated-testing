package com.learn.automated.testing.demo.features.promo.requests

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.validation.constraints.NotNull

data class PromoRequest @JsonCreator constructor (
        @JsonProperty("book_id")
        @field:NotNull(message = "promo.book.id.notnull")
        val bookId: Long? = 0,

        @field:NotNull(message = "promo.start.date.notnull")
        @JsonProperty("start_date")
        val startDate: Date?,

        @field:NotNull(message = "promo.end.date.notnull")
        @JsonProperty("end_date")
        val endDate: Date?
)