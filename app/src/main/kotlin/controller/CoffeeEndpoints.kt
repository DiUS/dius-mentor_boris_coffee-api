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
  fun addCoffee(
    @PathVariable orderId: Long,
    @RequestBody request: AddCoffeeRequest
  ): ResponseEntity<Any> {
    val order = orderService.findOneByNumber(orderId) ?: return orderNotFound(orderId)

    val style = CoffeeStyle.from(request.style) ?: return badProperty(style = request.style, orderId = orderId)
    val size = CoffeeSize.from(request.size) ?: return badProperty(size = request.size, orderId = orderId)

    val coffee = service.save(Coffee(style=style, size=size, parent=order))
    order.coffees.add(coffee)
    orderService.save(order)

    return ResponseEntity(
      AddCoffeeResponse.from(coffee, order.number),
      HttpStatus.CREATED
    )
  }

  @GetMapping("/{coffeeId}")
  fun getCoffee(@PathVariable orderId: Long, @PathVariable coffeeId: Long): ResponseEntity<Any> {
    val order = orderService.findOneByNumber(orderId) ?: return OrderEndpoints.orderNotFound(orderId)
    val coffee = order.coffees.firstOrNull { it.number == coffeeId } ?: return coffeeNotFound(orderId, coffeeId)

    return ResponseEntity(GetCoffeeResponse.from(coffee), HttpStatus.OK)
  }

  @PatchMapping("/{coffeeId}")
  fun updateCoffee(
    @PathVariable orderId: Long,
    @PathVariable coffeeId: Long,
    @RequestBody request: UpdateCoffeeRequest
  ): ResponseEntity<Any> {
    val order = orderService.findOneByNumber(orderId) ?: return OrderEndpoints.orderNotFound(orderId)
    val coffee = order.coffees.firstOrNull { it.number == coffeeId } ?: return coffeeNotFound(orderId, coffeeId)

    val style =
      if (request.style == null) { null }
      else {
        CoffeeStyle.from(request.style) ?:
          return badProperty(style = request.style, orderId = orderId, coffeeId = coffeeId)
      }
    val size =
      if (request.size == null) { null }
      else {
        CoffeeSize.from(request.size) ?:
          return badProperty(size = request.size, orderId = orderId, coffeeId = coffeeId)
      }

    return ResponseEntity(
      UpdateCoffeeResponse.from(
        service.save(coffee.patch(newStyle=style, newSize=size)),
        order.number
      ),
      HttpStatus.OK
    )
  }

  @DeleteMapping("/{coffeeId}")
  fun cancelCoffee(@PathVariable orderId: Long, @PathVariable coffeeId: Long): ResponseEntity<Any> {
    /*val order =*/ orderService.findOneByNumber(orderId) ?: return OrderEndpoints.orderNotFound(orderId)
    val coffee = service.deleteByNumber(coffeeId)
    return when (coffee) {
      null -> coffeeNotFound(coffeeId, orderId)
      else -> ResponseEntity(CancelCoffeeResponse.from(coffee, coffee.parent.number), HttpStatus.OK)
    }
  }

  companion object {

    fun orderNotFound(orderId: Long): ResponseEntity<Any> =
      ResponseEntity(
        RejectionResponse.fromCoffee("Order with id ${orderId} not found", orderId),
        HttpStatus.NOT_FOUND
      )

    fun coffeeNotFound(orderId: Long, coffeeId: Long): ResponseEntity<Any> =
      ResponseEntity(
        RejectionResponse.from("Coffee with id ${coffeeId} not found", orderId, coffeeId),
        HttpStatus.NOT_FOUND
      )

    fun badProperty(size: String? = null, style: String? = null, orderId: Long, coffeeId: Long? = null): ResponseEntity<Any> {
      val message =
        if (size != null) {
          badPropertyMessage(size, "size")
        } else if (style != null) {
          badPropertyMessage(style, "style")
        } else {
          throw IllegalArgumentException("One of size or style must be present.")
        }

      val response =
        if (coffeeId == null) {
          RejectionResponse.fromCoffee(message, orderId)
        } else {
          RejectionResponse.from(message, orderId, coffeeId)
        }

      return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    fun badPropertyMessage(prop: String, propName: String) =
      "${prop} is not a recognised ${propName}"

  }

}
