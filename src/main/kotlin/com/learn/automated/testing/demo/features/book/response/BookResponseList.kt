package com.learn.automated.testing.demo.features.book.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.common.features.book.BookResponse
import com.learn.common.shared.BaseResponse

class BookResponseList @JsonCreator
constructor(
        @JsonProperty("status") status: Boolean,
        @JsonProperty("message") message: String,
        @JsonProperty("items") responses: List<BookResponse>
) : BaseResponse(status, message) {

    private val responses: List<BookResponse>? = responses

    fun getItems(): List<BookResponse>? = responses

}