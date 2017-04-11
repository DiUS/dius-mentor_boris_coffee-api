package au.com.dius.coffee.dto

data class CreateOrderResponse(
  val id: Long,
  val path: String = "/order/${id}"
)
