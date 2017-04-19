package au.com.dius.coffee.controller

import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import au.com.dius.coffee.dto.*
import au.com.dius.coffee.model.CoffeeOrder
import au.com.dius.coffee.model.OrderRepository
import au.com.dius.coffee.service.OrderService

@RestController
@RequestMapping("/order")
class OrderEndpoints(repo: OrderRepository, env: Environment) {

  private val service: OrderService

  init {
    service = OrderService(repo, env)
  }

  @GetMapping
  fun listOrders() =
    ListOrdersResponse.from(service.findAll())

  @PostMapping
  fun createOrder() =
    ResponseEntity(
      CreateOrderResponse.from(
        service.save(CoffeeOrder())
      ),
      HttpStatus.CREATED
    )

  @GetMapping("/{orderId}")
  fun getOrder(@PathVariable orderId: Long): ResponseEntity<Any> {
    val order = service.findOneByNumber(orderId)
    return when (order) {
      null -> orderNotFound(orderId)
      else -> ResponseEntity(
        GetOrderResponse.from(order),
        HttpStatus.OK
      )
    }
  }

  @PatchMapping("/{orderId}")
  fun updateOrder(@PathVariable orderId: Long, @RequestBody request: UpdateOrderRequest): ResponseEntity<Any> {
    val order = service.findOneByNumber(orderId)
    return when (order) {
      null -> orderNotFound(orderId)
      else -> ResponseEntity(
        UpdateOrderResponse.from(
          service.save(order.patch(request))
        ),
        HttpStatus.OK
      )
    }
  }

  @DeleteMapping("/{orderId}")
  fun cancelOrder(@PathVariable orderId: Long): ResponseEntity<Any> {
    val order = service.deleteByNumber(orderId)
    return when (order) {
      null -> orderNotFound(orderId)
      else -> ResponseEntity(CancelOrderResponse.from(order), HttpStatus.NO_CONTENT)
    }
  }

  companion object {

    fun orderNotFound(orderId: Long): ResponseEntity<Any> =
      ResponseEntity(
        RejectionResponse.fromOrder("Order with id ${orderId} not found", orderId),
        HttpStatus.NOT_FOUND
      )

  }

}
