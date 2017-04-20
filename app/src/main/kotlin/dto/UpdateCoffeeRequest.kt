package au.com.dius.coffee.dto

data class UpdateCoffeeRequest(
  val style: String? = null,
  val size: String? = null
)
