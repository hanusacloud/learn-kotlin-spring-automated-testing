package com.learn.automated.testing.demo.features.book.services

import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookDetailResponse

interface BookService {

    fun create(bookRequest: BookRequest): BookDetailResponse

    fun update(id: Long, bookRequest: BookRequest): BookDetailResponse

}