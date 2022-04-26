package com.mercadolivro.repository

import com.mercadolivro.models.CustomerModel
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CustomerRepository: CrudRepository<CustomerModel, Int> {
  @Query("SELECT c FROM customer c WHERE c.name LIKE %:name%")
  fun findByNameContaining(name: String): List<CustomerModel>
  fun existsByEmail(email: String): Boolean
}
