package com.mercadolivro.services

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
  private val bookRepository: BookRepository
) {

  fun create(book: BookModel): BookModel {
    return bookRepository.save(book)
  }

  fun findAll(pageable: Pageable): Page<BookModel> {
    return bookRepository.findAll(pageable)
  }

  fun getById(id: Int): BookModel {
    return bookRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code) }
  }

  fun getActives(pageable: Pageable): Page<BookModel> {
    return bookRepository.findByStatus(BookStatus.ENABLED, pageable)
  }

  fun update(book: BookModel) {
    bookRepository.save(book)
  }

  fun delete(id: Int) {
    if (!bookRepository.existsById(id)) {
      throw NotFoundException("Book [$id] not found", "ML-101")
    }
    bookRepository.deleteById(id)
  }

  fun deleteByCustomer(customer: CustomerModel) {
    val books = bookRepository.findByCustomer(customer)
    books.forEach {
      it.status = BookStatus.DELETED
    }
    bookRepository.saveAll(books)
  }
}
