package com.learn.automated.testing.demo.features.book.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.models.Book
import java.util.*

class BookItem {

    private val id: Long
    private val title: String
    private val totalPage: Int
    private val createdAt: Date?

    @JsonCreator
    constructor(
            @JsonProperty("id") id: Long,
            @JsonProperty("title") title: String,
            @JsonProperty("total_page") totalPage: Int,
            @JsonProperty("created_at") createdAt: Date?
    ) {
        this.id = id
        this.title = title
        this.totalPage = totalPage
        this.createdAt = createdAt
    }

    constructor(
            book: Book
    ) {
        this.id = book.id ?: 0
        this.title = book.title
        this.totalPage = book.totalPage
        this.createdAt = book.createdAt
    }

    fun getTitle(): String = title

    fun getId(): Long = id

    fun getTotalPage(): Int = totalPage

    fun getCreatedAt(): Date? = createdAt

}