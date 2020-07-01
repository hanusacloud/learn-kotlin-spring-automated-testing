package com.learn.automated.testing.demo.shared.responses

import com.fasterxml.jackson.annotation.JsonProperty

open class BaseResponse constructor (
        @JsonProperty("status")
        private val status: Boolean,
        @JsonProperty("message")
        private val message: String,
        @JsonProperty("error_messages")
        private val errorMessages: List<String> = emptyList<String>()
) {

    fun getStatus(): Boolean = status

    fun getMessage(): String = message

    fun getErrorMessages(): List<String> = errorMessages

}