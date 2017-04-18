package au.com.dius.coffee.dto

data class RejectionResponse(
  val message: String,
  val path: String
) {

  companion object {

    fun fromOrder(message: String, orderId: Long) =
      RejectionResponse(
        message = message,
        path = "/order/${orderId}"
      )

    fun fromCoffee(message: String, orderId: Long) =
      RejectionResponse(
        message = message,
        path = "/order/${orderId}/coffee"
      )

    fun from(message: String, orderId: Long, coffeeId: Long) =
      RejectionResponse(
        message = message,
        path = "/order/${orderId}/coffee/${coffeeId}"
      )

  }

}
