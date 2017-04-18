package au.com.dius.coffee.dto

import au.com.dius.coffee.model.CoffeeOrder

data class CreateOrderResponse(
  val id: Long,
  val path: String
) {

  companion object {

    fun from(order: CoffeeOrder) =
      CreateOrderResponse(
        id = order.number,
        path = "/order/${order.number}"
      )

  }

}
