package au.com.dius.coffee.model

enum class CoffeeStyle(val displayName: String) {
  LATTE("Latte"),
  FLAT_WHITE("Flat White"),
  CAPPUCCINO("Cappuccino"),

  LONG_MAC("Long Macchiatto"),
  SHORT_MAC("Short Macchiatto"),

  LONG_BLACK("Long Black"),
  SHORT_BLACK("Short Black"),

  MAGIC("Magic");

  companion object {

    fun from(displayName: String) =
      values().firstOrNull { it.displayName == displayName }

  }

}

enum class CoffeeSize(val displayName: String) {
  LARGE("Large"),
  REGULAR("Regular"),

  THREE_QUARTER("3/4"),
  HALF("1/2"),

  PICCOLO("Piccolo");

  companion object {

    fun from(displayName: String) =
      values().firstOrNull { it.displayName == displayName }

  }

}
