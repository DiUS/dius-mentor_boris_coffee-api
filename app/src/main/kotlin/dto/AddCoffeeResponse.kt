package au.com.dius.coffee.dto

import au.com.dius.coffee.model.Coffee

data class AddCoffeeResponse(
  val id: Long,
  val path: String
) {

  companion object {

    fun from(coffee: Coffee, orderId: Long) =
      AddCoffeeResponse(
        id = coffee.number,
        path = "/order/${orderId}/coffee/${coffee.number}"
      )

  }

}
