package au.com.dius.coffee.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

import au.com.dius.coffee.dto.*
import au.com.dius.coffee.model.CoffeeOrder
import au.com.dius.coffee.model.OrderRepository

@RestController
@RequestMapping("/order")
class OrderEndpoints(val repo: OrderRepository) {

  @GetMapping
  fun listOrders() =
    ListOrdersResponse.from(repo.findAll())

  @PostMapping
  fun createOrder() =
    ResponseEntity(
      CreateOrderResponse(
        repo.save(CoffeeOrder()).number
      ),
      HttpStatus.CREATED
    )

  @GetMapping("/{orderId}")
  fun getOrder(@PathVariable orderId: Long): ResponseEntity<Any> {
    val order = repo.findOneByNumber(orderId)
    return when (order) {
      null -> ResponseEntity.notFound().build()
      else -> ResponseEntity(GetOrderResponse.from(order), HttpStatus.OK)
    }
  }

  @PatchMapping("/{orderId}")
  fun updateOrder(@PathVariable orderId: Long, @RequestBody request: UpdateOrderRequest) =
    UpdateOrderResponse(orderId)

  @DeleteMapping("/{orderId}")
  fun cancelOrder(@PathVariable orderId: Long): ResponseEntity<Any> {
    val orders = repo.deleteByNumber(orderId)
    return when (orders.size) {
      0 -> ResponseEntity.notFound().build()
      else -> ResponseEntity(CancelOrderResponse(orders[0].number), HttpStatus.NO_CONTENT)
    }
  }

}
