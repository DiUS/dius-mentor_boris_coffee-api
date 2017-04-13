package au.com.dius.coffee.dto

import au.com.dius.coffee.model.CoffeeOrder

data class CancelOrderResponse(
  val id: Long,
  val path: String = "/order/${id}"
) {

  companion object {

    fun from(order: CoffeeOrder) =
      CancelOrderResponse(id=order.number)

  }

}
