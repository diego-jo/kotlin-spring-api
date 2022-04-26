package com.mercadolivro.controllers

import com.mercadolivro.controllers.request.PostBookRequest
import com.mercadolivro.controllers.request.PutBookRequest
import com.mercadolivro.controllers.response.BookResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.extensions.toBookModel
import com.mercadolivro.extensions.toResponse
import com.mercadolivro.services.BookService
import com.mercadolivro.services.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("books")
class BookController(
  private val customerService: CustomerService,
  private val bookService: BookService
) {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun create(@RequestBody @Valid request: PostBookRequest): BookResponse {
    return bookService.create(request.toBookModel(customerService.getById(request.customerId))).toResponse()
  }

  @GetMapping
  fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
    return bookService.findAll(pageable).map { it.toResponse() }
  }

  @GetMapping("/active")
  fun getActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
    return bookService.getActives(pageable).map { it.toResponse() }
  }

  @GetMapping("/{id}")
  fun getById(@PathVariable id: Int): BookResponse {
    return bookService.getById(id).toResponse()
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun update(@RequestBody book: PutBookRequest, @PathVariable id: Int) {
    val previousBook = bookService.getById(id)
    bookService.update(book.toBookModel(previousBook))
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun delete(@PathVariable id: Int) {
    val book = bookService.getById(id)
    book.status = BookStatus.CANCELED
    bookService.update(book)
  }
}
