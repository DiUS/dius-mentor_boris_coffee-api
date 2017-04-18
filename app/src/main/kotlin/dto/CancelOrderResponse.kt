package au.com.dius.coffee.dto

import au.com.dius.coffee.model.CoffeeOrder

data class CancelOrderResponse(
  val id: Long,
  val path: String
) {

  companion object {

    fun from(order: CoffeeOrder) =
      CancelOrderResponse(
        id=order.number,
        path = "/order/${order.number}"
      )

  }

}
