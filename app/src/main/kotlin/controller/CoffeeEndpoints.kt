package au.com.dius.coffee.controller

import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import au.com.dius.coffee.dto.*
import au.com.dius.coffee.model.*
import au.com.dius.coffee.service.CoffeeService
import au.com.dius.coffee.service.OrderService

@RestController
@RequestMapping("/order/{orderId}/coffee")
class CoffeeEndpoints(orderRepo: OrderRepository, repo: CoffeeRepository, env: Environment) {

  private val orderService: OrderService
  private val service: CoffeeService

  init {
    orderService = OrderService(orderRepo, env)
    service = CoffeeService(repo, env)
  }

  @PostMapping
  fun addCoffee(@PathVariable orderId: Long, @RequestBody request: AddCoffeeRequest): ResponseEntity<Any> {
    val order = orderService.findOneByNumber(orderId) ?: return ResponseEntity.notFound().build()

    val style = CoffeeStyle.from(request.style) ?:
      return ResponseEntity(
        RejectionResponse.fromCoffee("${request.style} is not a recognised style", orderId),
        HttpStatus.BAD_REQUEST
      )
    val size = CoffeeSize.from(request.size) ?:
      return ResponseEntity(
        RejectionResponse.fromCoffee("${request.size} is not a recognised size", orderId),
        HttpStatus.BAD_REQUEST
      )

    val coffee = service.save(Coffee(style=style, size=size))
    order.coffees.add(coffee)
    orderService.save(order)

    return ResponseEntity(
      AddCoffeeResponse.from(coffee, order.number),
      HttpStatus.CREATED
    )
  }

}
