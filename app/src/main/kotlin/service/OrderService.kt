package au.com.dius.coffee.service

import org.springframework.core.env.Environment

import au.com.dius.coffee.model.CoffeeOrder
import au.com.dius.coffee.model.OrderRepository

class OrderService(val repo: OrderRepository, env: Environment) {

  private val isTest: Boolean

  init {
    isTest = "test" in env.activeProfiles
  }

  fun findAll(): Iterable<CoffeeOrder> =
    if (isTest) {
      StubOrderRepo.findAll
    } else {
      repo.findAll()
    }

  fun save(order: CoffeeOrder): CoffeeOrder =
    if (isTest) {
      order
    } else {
      repo.save(order)
    }

  fun findOneByNumber(number: Long): CoffeeOrder? =
    if (isTest) {
      StubOrderRepo.findOneByNumber
    } else {
      repo.findOneByNumber(number)
    }

  fun deleteByNumber(number: Long): CoffeeOrder? =
    if (isTest) {
      StubOrderRepo.deleteByNumber
    } else {
      val orders = repo.deleteByNumber(number)
      when (orders.size) {
        0 -> null
        else -> orders[0]
      }
    }

}
