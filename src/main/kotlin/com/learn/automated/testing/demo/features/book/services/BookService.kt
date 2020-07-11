package com.learn.automated.testing.demo.features.book.services

import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import com.learn.common.features.book.BookDetailResponse

interface BookService {

    fun detail(bookId: Long): BookDetailResponse

    fun getAll(): BookResponseList

    fun create(bookRequest: BookRequest): BookDetailResponse

    fun update(id: Long, bookRequest: BookRequest): BookDetailResponse

}