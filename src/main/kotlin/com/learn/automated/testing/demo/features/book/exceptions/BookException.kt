package com.learn.automated.testing.demo.features.book.exceptions

class BookException(message: String?) : Exception(message) {

    companion object {

        fun notFound(): BookException = BookException("Book not found!");

    }

}