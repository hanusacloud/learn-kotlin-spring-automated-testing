package com.learn.automated.testing.demo.features.book.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.models.Book

class BookItem {

    private val id: Long
    private val title: String
    private val totalPage: Int

    @JsonCreator
    constructor(
            @JsonProperty("id") id: Long,
            @JsonProperty("title") title: String,
            @JsonProperty("total_page") totalPage: Int
    ) {
        this.id = id
        this.title = title
        this.totalPage = totalPage
    }

    constructor(
            book: Book
    ) {
        this.id = book.id
        this.title = book.title
        this.totalPage = book.totalPage
    }

    fun getTitle(): String = title

}