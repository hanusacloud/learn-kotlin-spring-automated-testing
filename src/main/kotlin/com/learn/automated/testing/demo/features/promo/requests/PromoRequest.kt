package com.learn.automated.testing.demo.features.promo.requests

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class PromoRequest @JsonCreator constructor (
        @JsonProperty("book_id") var bookId: Long,
        @JsonProperty("start_date") var startDate: Date,
        @JsonProperty("end_date") var endDate: Date
)