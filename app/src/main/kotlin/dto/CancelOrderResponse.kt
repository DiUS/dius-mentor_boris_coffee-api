package au.com.dius.coffee.dto

data class CancelOrderResponse(
  val id: Long,
  val path: String = "/order/${id}"
)
