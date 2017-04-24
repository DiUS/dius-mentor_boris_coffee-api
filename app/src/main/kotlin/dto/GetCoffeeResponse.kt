package au.com.dius.coffee.dto

import au.com.dius.coffee.model.Coffee

data class GetCoffeeResponse(
  val id: Long,
  val style: String,
  val size: String,
  val path: String
) {

  companion object {

    fun from(coffee: Coffee) =
      GetCoffeeResponse(
        id=coffee.number,
        style=coffee.style.displayName,
        size=coffee.size.displayName,
        path="/order/${coffee.parent.number}/coffee/${coffee.number}"
      )

  }

}
