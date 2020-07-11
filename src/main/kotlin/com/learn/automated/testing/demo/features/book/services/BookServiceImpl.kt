package com.learn.automated.testing.demo.features.book.services

import com.learn.automated.testing.demo.features.book.BookRepository
import com.learn.automated.testing.demo.features.book.exceptions.BookException
import com.learn.automated.testing.demo.features.book.models.Book
import com.learn.automated.testing.demo.features.book.request.BookRequest
import com.learn.automated.testing.demo.features.book.response.BookResponseList
import com.learn.automated.testing.demo.features.category.exceptions.CategoryException
import com.learn.automated.testing.demo.features.category.models.Category
import com.learn.automated.testing.demo.features.category.repositories.CategoryRepository
import com.learn.common.features.book.BookResponse
import com.learn.common.features.book.BookDetailResponse
import com.learn.common.features.category.CategoryResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class BookServiceImpl constructor(
        val repository: BookRepository,
        val categoryRepository: CategoryRepository
) : BookService {

    override fun detail(bookId: Long): BookDetailResponse {
        val book: Book = repository.findById(bookId)
                .orElseThrow { BookException.notFound() }

        return BookDetailResponse(
                true,
                "success",
                generateBookResponse(book)
        )
    }

    override fun getAll(): BookResponseList {
        val book: List<Book> = repository.findAll()
        return BookResponseList(
                true,
                "success",
                book.map { e -> generateBookResponse(e) }.toList())
    }

    override fun create(bookRequest: BookRequest): BookDetailResponse {
        val categoryOptional: Optional<Category> = categoryRepository.findById(
                bookRequest.categoryId!!
        )
        if (!categoryOptional.isPresent) {
            throw CategoryException.notFound()
        }
        val book = Book(
                title = bookRequest.title!!,
                totalPage = bookRequest.totalPage!!,
                price = bookRequest.price!!,
                category = categoryOptional.get()
        )
        return BookDetailResponse(
                true,
                "success",
                generateBookResponse(repository.save(book))
        )
    }

    @Throws(BookException::class)
    override fun update(id: Long, bookRequest: BookRequest): BookDetailResponse {
        val bookOptional: Optional<Book> = repository.findById(id)
        if (!bookOptional.isPresent) {
            throw BookException.notFound()
        }
        val book = bookOptional.get()
        book.setTitle(bookRequest.title!!)
        book.setTotalPage(bookRequest.totalPage!!)
        book.setPrice(bookRequest.price!!)
        return BookDetailResponse(
                true,
                "success",
                generateBookResponse(repository.save(book))
        )
    }

    private fun generateBookResponse(book: Book): BookResponse {
        return BookResponse(
                book.getId()!!,
                book.getTitle(),
                book.getTotalPage(),
                book.getCreatedAt(),
                book.getPrice(),
                CategoryResponse(book.getCategory().getId()!!, book.getCategory().getName())
        )
    }

}