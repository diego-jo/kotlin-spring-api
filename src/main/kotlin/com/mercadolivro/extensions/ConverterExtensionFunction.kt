package com.mercadolivro.extensions

import com.mercadolivro.controllers.request.PostBookRequest
import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutBookRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.controllers.response.BookResponse
import com.mercadolivro.controllers.response.CustomerResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.models.BookModel
import com.mercadolivro.models.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
  return CustomerModel(
    name = this.name,
    email = this.email,
    status = CustomerStatus.ENABLED
  )
}

fun PutCustomerRequest.toCustomerModel(previousCustomer: CustomerModel): CustomerModel {
  return CustomerModel(
    id = previousCustomer.id,
    name = this.name,
    email = this.email,
    status = previousCustomer.status
  )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
  return BookModel(
    name = this.name,
    price = this.price,
    status = BookStatus.ENABLED,
    customer = customer
  )
}

fun PutBookRequest.toBookModel(previousBook: BookModel): BookModel {
  return BookModel(
    id = previousBook.id,
    name = this.name ?: previousBook.name,
    price = this.price ?: previousBook.price,
    status = previousBook.status!!,
    customer = previousBook.customer
  )
}

fun CustomerModel.toResponse(): CustomerResponse {
  return CustomerResponse(
    id = this.id,
    name = this.name,
    email = this.email,
    status = this.status
  )
}

fun BookModel.toResponse(): BookResponse {
  return BookResponse(
    id = this.id,
    name = this.name,
    price = this.price,
    customer = this.customer,
    status = this.status
  )
}
