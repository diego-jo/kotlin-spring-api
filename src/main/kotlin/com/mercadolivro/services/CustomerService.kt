package com.mercadolivro.services

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exceptions.NotFoundException
import com.mercadolivro.models.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
  private val customerRepository: CustomerRepository,
  private val bookService: BookService
) {

  fun getAll(name: String?): List<CustomerModel> {
    name?.let {
      return customerRepository.findByNameContaining(it)
    }
    return customerRepository.findAll().toList()
  }

  fun getById(id: Int): CustomerModel {
    return customerRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code) }
  }

  fun create(customer: CustomerModel): CustomerModel {
    return customerRepository.save(customer)
  }

  fun update(customer: CustomerModel) {
    if (!customerRepository.existsById(customer.id!!)){
      throw Exception()
    }
    customerRepository.save(customer)
  }

  fun delete(id: Int) {
    val customer = getById(id)
    bookService.deleteByCustomer(customer)
    customer.status = CustomerStatus.DISABLED
    customerRepository.save(customer)
  }

  fun emailAvailable(email: String): Boolean {
    return !customerRepository.existsByEmail(email)
  }
}
