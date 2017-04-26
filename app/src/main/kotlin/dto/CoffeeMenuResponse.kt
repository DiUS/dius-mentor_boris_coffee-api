package au.com.dius.coffee.dto

import au.com.dius.coffee.model.CoffeeSize
import au.com.dius.coffee.model.CoffeeStyle

data class CoffeeMenuResponse(
  val style: List<String>,
  val size: List<String>,
  val path: String
) {

  companion object {

    fun default() =
      CoffeeMenuResponse(
        style=CoffeeStyle.values().map { it.displayName },
        size=CoffeeSize.values().map { it.displayName },
        path="/menu/coffee"
      )

  }

}
