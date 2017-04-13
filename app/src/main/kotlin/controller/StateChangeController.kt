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
      "empty order 19" -> `empty order 19`()
    }

    return ResponseEntity.ok().build()
  }

  private fun `no orders`() {
    StubOrderRepo.findAll = emptyList()
  }

  private fun `empty order 19`() {
    StubOrderRepo.findOneByNumber = CoffeeOrder(number=19)
  }

}
