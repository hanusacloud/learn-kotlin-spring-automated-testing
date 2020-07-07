package com.learn.automated.testing.demo.features.category.responses

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CategoryResponse @JsonCreator constructor(
        @JsonProperty("id")
        private val id: Long,
        @JsonProperty("name")
        private val name: String
) {

        fun getName(): String = name

}