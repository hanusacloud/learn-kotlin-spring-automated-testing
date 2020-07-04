package com.learn.automated.testing.demo.features.book.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BookRequest @JsonCreator constructor (
        @JsonProperty("title")
        @field:NotNull(message = "book.fill.title")
        @field:NotBlank(message = "book.title.notblank")
        val title: String?,

        @field:NotNull(message = "book.total.page.notnull")
        @field:Min(value = 1, message = "book.total.page.greater.than.zero")
        @JsonProperty("total_page")
        val totalPage: Int?,

        @field:NotNull(message = "book.fill.price")
        @field:Min(value = 1, message = "book.price.greater.than.zero")
        @JsonProperty("price")
        val price: Long?
)