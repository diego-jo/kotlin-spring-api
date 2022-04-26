package com.mercadolivro.controllers

import com.mercadolivro.controllers.request.PostCustomerRequest
import com.mercadolivro.controllers.request.PutCustomerRequest
import com.mercadolivro.controllers.response.CustomerResponse
import com.mercadolivro.extensions.toCustomerModel
import com.mercadolivro.extensions.toResponse
import com.mercadolivro.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(
  private val customerService: CustomerService
) {

  @GetMapping
  fun getAll(@RequestParam name: String?): List<CustomerResponse> {
    return customerService.getAll(name).map { it.toResponse() }
  }

  @GetMapping("/{id}")
  fun getById(@PathVariable id: Int): CustomerResponse {
    return customerService.getById(id).toResponse()
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun create(@RequestBody @Valid customer: PostCustomerRequest): CustomerResponse {
    return customerService.create(customer.toCustomerModel()).toResponse()
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun update(@RequestBody @Valid customer: PutCustomerRequest, @PathVariable id: Int) {
    val previousCustomer = customerService.getById(id)
    customerService.update(customer.toCustomerModel(previousCustomer))
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun delete(@PathVariable id: Int) {
    customerService.delete(id)
  }
}
