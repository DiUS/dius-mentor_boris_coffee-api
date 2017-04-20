package au.com.dius.coffee.dto

import au.com.dius.coffee.model.Coffee

data class UpdateCoffeeResponse(
  val id: Long,
  val path: String
) {

  companion object {

    fun from(coffee: Coffee, orderId: Long) =
      UpdateCoffeeResponse(
        id=coffee.number,
        path="/order/${orderId}/coffee/${coffee.number}"
      )

  }

}
