package com.learn.automated.testing.demo.features.promo.requests

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*
import javax.validation.constraints.NotNull

data class PromoRequest @JsonCreator constructor (
        @JsonProperty("book_id")
        val bookId: Long = 0,
        @field:NotNull(message = "Start date can not be null!")
        @JsonProperty("start_date")
        val startDate: Date?,
        @field:NotNull(message = "End date can not be null!")
        @JsonProperty("end_date")
        val endDate: Date?
)