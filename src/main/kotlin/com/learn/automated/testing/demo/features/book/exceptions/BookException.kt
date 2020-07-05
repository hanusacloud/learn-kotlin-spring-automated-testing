package com.learn.automated.testing.demo.features.book.exceptions

import com.learn.automated.testing.demo.shared.utils.Translation

class BookException(message: String?) : Exception(message) {

    companion object {

        fun notFound(): BookException = BookException(Translation.getMessage("book.notfound"))

    }

}