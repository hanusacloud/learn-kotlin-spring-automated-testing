package com.learn.automated.testing.demo.features.book.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.learn.automated.testing.demo.features.book.models.Book
import java.util.*

class BookResponse {

    private val id: Long
    private val title: String
    private val totalPage: Int
    private val createdAt: Date?
    private val price: Long

    @JsonCreator
    constructor(
            @JsonProperty("id") id: Long,
            @JsonProperty("title") title: String,
            @JsonProperty("total_page") totalPage: Int,
            @JsonProperty("created_at") createdAt: Date?,
            @JsonProperty("price") price: Long
    ) {
        this.id = id
        this.title = title
        this.totalPage = totalPage
        this.createdAt = createdAt
        this.price = price
    }

    constructor(
            book: Book
    ) {
        this.id = book.getId()!!
        this.title = book.getTitle()
        this.totalPage = book.getTotalPage()
        this.createdAt = book.getCreatedAt()
        this.price = book.getPrice()
    }

    fun getTitle(): String = title

    fun getId(): Long = id

    fun getTotalPage(): Int = totalPage

    fun getCreatedAt(): Date? = createdAt

    fun getPrice(): Long = price

}