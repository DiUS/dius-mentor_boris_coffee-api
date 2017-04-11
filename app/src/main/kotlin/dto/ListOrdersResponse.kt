package au.com.dius.coffee.dto

import au.com.dius.coffee.model.CoffeeOrder

data class ListOrdersResponse(
  val orders: List<ShortOrder>
) {

  data class ShortOrder(
    val id: Long,
    val path: String = "/order/${id}"
  )

  companion object {

    fun from(orderEntities: Iterable<CoffeeOrder>) =
      ListOrdersResponse(orderEntities.map { ShortOrder(it.number) })

  }

}
