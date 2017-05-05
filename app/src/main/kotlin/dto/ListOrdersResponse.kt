package au.com.dius.coffee.dto

import au.com.dius.coffee.model.Coffee
import au.com.dius.coffee.model.CoffeeOrder

data class ListOrdersResponse(
  val orders: List<ShortOrder>
) {

  data class ShortOrder(
    val id: Long,
    val path: String,
    val name: String,
    val coffeeSummaries: List<String>
  ) {

    companion object {

      fun from(order: CoffeeOrder) =
        ShortOrder(
          id=order.number,
          path="/order/${order.number}",
          name=order.name,
          coffeeSummaries=order.coffees.map { summaryFrom(it) }
        )

      fun summaryFrom(coffee: Coffee) =
        "${coffee.size.displayName} ${coffee.style.displayName}"

    }

  }

  companion object {

    fun from(orderEntities: Iterable<CoffeeOrder>) =
      ListOrdersResponse(orderEntities.map { ShortOrder.from(it) })

  }

}
