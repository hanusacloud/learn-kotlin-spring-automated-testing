package com.learn.automated.testing.demo.features.book.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class BookRequest @JsonCreator constructor (
        @JsonProperty("title")
        @field:NotBlank(message = "Title can not be empty!")
        val title: String,
        @field:Min(value = 1, message = "Fill total page!")
        @JsonProperty("total_page")
        val totalPage: Int,
        @field:Min(value = 1, message = "Fill the price!")
        @JsonProperty("price")
        val price: Long
)