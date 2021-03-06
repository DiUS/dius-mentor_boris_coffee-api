package au.com.dius.coffee.dto

import au.com.dius.coffee.model.Coffee
import au.com.dius.coffee.model.CoffeeOrder
import au.com.dius.coffee.model.CoffeeSize

data class GetOrderResponse(
  val id: Long,
  val coffees: List<ShortCoffee>,
  val name: String,
  val path: String
) {

  data class ShortCoffee(
    val id: Long,
    val summary: String,
    val path: String
  ) {

    companion object {

      fun from(coffee: Coffee, orderId: Long) =
        ShortCoffee(
          id=coffee.number,
          summary=summaryFrom(coffee),
          path="/order/${orderId}/coffee/${coffee.number}"
        )

      fun summaryFrom(coffee: Coffee) =
        "${summaryFrom(coffee.size)}${coffee.style.displayName}"

      private fun summaryFrom(size: CoffeeSize) =
        when (size) {
          CoffeeSize.REGULAR -> ""
          else -> "${size.displayName} "
        }

    }

  }

  companion object {

    fun from(order: CoffeeOrder) =
      GetOrderResponse(
        id=order.number,
        coffees=order.coffees.map { ShortCoffee.from(it, order.number) },
        name=order.name,
        path="/order/${order.number}"
      )

  }

}
