package au.com.dius.coffee.dto

data class MenuResponse(
  val coffee: String,
  val path: String
) {

  companion object {

    fun default() =
      MenuResponse(
        coffee="/menu/coffee",
        path="/menu"
      )

  }

}
