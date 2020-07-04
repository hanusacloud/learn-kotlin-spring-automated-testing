package com.learn.automated.testing.demo.features.book.exceptions

import com.learn.automated.testing.demo.shared.utils.Translation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource

class BookException(message: String?) : Exception(message) {

    @Autowired
    private lateinit var messageSource: MessageSource

    companion object {

        fun notFound(): BookException = BookException(Translation.getMessage("book.notfound"));

    }

}