package com.mercadolivro.controllers.request

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequest(
  @field:NotEmpty(message = "name cannot be empty")
  val name: String,

  @field:Email(message = "email must be valid")
  val email: String
)
