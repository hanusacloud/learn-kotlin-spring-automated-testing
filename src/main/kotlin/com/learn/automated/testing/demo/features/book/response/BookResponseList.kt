package com.learn.automated.testing.demo.features.book.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.shared.responses.BaseResponse

class BookResponseList @JsonCreator
constructor(
        @JsonProperty("status") status: Boolean,
        @JsonProperty("message") message: String,
        @JsonProperty("items") items: List<BookItem>
) : BaseResponse(status, message) {

    private val items: List<BookItem>? = items

    fun getItems(): List<BookItem>? = items

}