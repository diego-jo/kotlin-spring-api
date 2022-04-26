package com.mercadolivro.controllers.request

import com.mercadolivro.validation.EmailAvailable
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PostCustomerRequest(
  @field:NotEmpty(message = "name cannot be empty")
  val name: String,

  @field:Email(message  = "email must be valid")
  @EmailAvailable
  val email: String
)
