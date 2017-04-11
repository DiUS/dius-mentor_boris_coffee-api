package au.com.dius.coffee.dto

import au.com.dius.coffee.model.CoffeeOrder

data class GetOrderResponse(
  val id: Long,
  val coffees: List<Coffee>,
  val name: String,
  val path: String
) {

  data class Coffee(
    val id: Long
  )

  companion object {

    fun from(order: CoffeeOrder) =
      GetOrderResponse(
        id=order.number,
        coffees=order.coffees.map { Coffee(it.number) },
        name=order.name,
        path="/order/${order.number}"
      )

  }

}
