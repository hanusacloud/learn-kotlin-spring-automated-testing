package com.learn.automated.testing.demo.features.book.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class BookRequest @JsonCreator constructor (
        @JsonProperty("title")
        @field:NotNull(message = "Title can not be empty!")
        @field:NotBlank(message = "Title can not be empty!")
        val title: String?,
        @field:NotNull(message = "Fill total page!")
        @field:Min(value = 1, message = "Total page must be greater than zero!")
        @JsonProperty("total_page")
        val totalPage: Int?,
        @field:NotNull(message = "Fill the price!")
        @field:Min(value = 1, message = "Price must be greater than zero!")
        @JsonProperty("price")
        val price: Long?
)