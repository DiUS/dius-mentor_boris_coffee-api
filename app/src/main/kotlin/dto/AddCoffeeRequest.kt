package au.com.dius.coffee.dto

data class AddCoffeeRequest(
  val style: String,
  val size: String = "Regular"
)
