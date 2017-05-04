package au.com.dius.coffee.controller

import org.springframework.context.annotation.Profile
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import au.com.dius.coffee.model.Coffee
import au.com.dius.coffee.model.CoffeeOrder
import au.com.dius.coffee.model.CoffeeSize
import au.com.dius.coffee.model.CoffeeStyle
import au.com.dius.coffee.service.StubOrderRepo
import au.com.dius.coffee.service.StubCoffeeRepo

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
      "order 23" -> `order 23`()
      "order 43 with coffee 59" -> `order 43 with coffee 59`()
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
    StubOrderRepo.findAll.forEach {
      it.coffees.add(Coffee(3, it, size=CoffeeSize.LARGE))
      it.coffees.add(Coffee(7, it, style=CoffeeStyle.CAPPUCCINO))
    }
  }

  private fun `empty order 19`() {
    val order = CoffeeOrder(number=19)
    StubOrderRepo.findOneByNumber = order
    StubOrderRepo.deleteByNumber = order
  }

  private fun `order 23`() {
    val order = CoffeeOrder(number=23)
    with(order) {
      name = "Jimothy"
      for (id in 66L..67L) {
        coffees.add(Coffee(number=id))
      }
    }
    StubOrderRepo.findOneByNumber = order
    StubOrderRepo.deleteByNumber = order
  }

  private fun `order 43 with coffee 59`() {
    val order = CoffeeOrder(number=43)
    val coffee = Coffee(number=59, parent=order)
    with(order) {
      name = "Bettilda"
      coffees.add(coffee)
    }
    StubOrderRepo.findOneByNumber = order
    StubOrderRepo.deleteByNumber = order
    StubCoffeeRepo.findOneByNumber = coffee
    StubCoffeeRepo.deleteByNumber = coffee
  }

}
