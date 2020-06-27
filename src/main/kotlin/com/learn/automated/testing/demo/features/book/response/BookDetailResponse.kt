package com.learn.automated.testing.demo.features.book.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.shared.responses.BaseResponse

class BookDetailResponse : BaseResponse {

    private val book: BookResponse?

    @JsonCreator
    constructor(
            @JsonProperty("status") status: Boolean,
            @JsonProperty("message") message: String,
            @JsonProperty("book") book: BookResponse?
    ) : super (status, message) {
        this.book = book
    }

    constructor(
            status: Boolean,
            message: String,
            book: Book
    ) : super (status, message) {
        this.book = BookResponse(book)
    }

    constructor(message: String) : super (false, message) {
        this.book = null
    }

    fun getBook(): BookResponse? = book

}