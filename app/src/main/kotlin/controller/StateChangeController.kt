package au.com.dius.coffee.controller

import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import au.com.dius.coffee.model.CoffeeOrder
import au.com.dius.coffee.service.StubOrderRepo

@Profile("test")
@RestController
@RequestMapping("/pactStateChange")
class StateChangeController {

  @PostMapping
  fun providerState(@RequestBody body: Map<String, Any>): ResponseEntity<Any> {
    when (body["state"]) {
      "no orders" -> `no orders`()
      "many orders" -> `many orders`()
      "empty order 19" -> `empty order 19`()
    }

    return ResponseEntity.ok().build()
  }

  private fun `no orders`() {
    StubOrderRepo.findAll = emptyList()
    StubOrderRepo.findOneByNumber = null
    StubOrderRepo.deleteByNumber = null
  }

  private fun `many orders`() {
    StubOrderRepo.findAll = listOf(
      CoffeeOrder(number=1),
      CoffeeOrder(number=2, name="Fred"),
      CoffeeOrder(number=5, name="Bill"),
      CoffeeOrder(number=6),
      CoffeeOrder(number=7, name="John"),
      CoffeeOrder(number=19, name="Jack"),
      CoffeeOrder(number=20, name="Boris")
    )
  }

  private fun `empty order 19`() {
    val order = CoffeeOrder(number=19)
    StubOrderRepo.findOneByNumber = order
    StubOrderRepo.deleteByNumber = order
  }

}
