package au.com.dius.coffee.dto

data class UpdateOrderResponse(
  val id: Long,
  val path: String = "/order/${id}"
)
