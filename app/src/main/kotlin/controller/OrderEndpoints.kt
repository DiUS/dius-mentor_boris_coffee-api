package au.dius.com.coffee.controller

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestMethod.*

import au.dius.com.coffee.dto.*

@RestController
class OrderEndpoints {

  @GetMapping("/order")
  fun listOrders() =
    ListOrdersResponse()

  @PostMapping("/order")
  fun createOrder() =
    CreateOrderResponse()

  @GetMapping("/order/{orderId}")
  fun getOrder(@PathVariable orderId: String) =
    GetOrderResponse()

  @PatchMapping("/order/{orderId}")
  fun updateOrder(@PathVariable orderId: String, @RequestBody request: UpdateOrderRequest) =
    UpdateOrderResponse()

  @DeleteMapping("/order/{orderId}")
  fun cancelOrder(@PathVariable orderId: String) =
    CancelOrderResponse()

}
