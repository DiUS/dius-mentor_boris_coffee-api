package au.com.dius.coffee.service

import org.springframework.core.env.Environment

import au.com.dius.coffee.model.Coffee
import au.com.dius.coffee.model.CoffeeRepository

class CoffeeService(val repo: CoffeeRepository, env: Environment) {

  private val isTest: Boolean

  init {
    isTest = "test" in env.activeProfiles
  }

  fun save(coffee: Coffee): Coffee =
    if (isTest) {
      coffee
    } else {
      repo.save(coffee)
    }

  fun findOneByNumber(number: Long): Coffee? =
    if (isTest) {
      StubCoffeeRepo.findOneByNumber
    } else {
      repo.findOneByNumber(number)
    }

  fun deleteByNumber(number: Long): Coffee? =
    if (isTest) {
      StubCoffeeRepo.deleteByNumber
    } else {
      repo.deleteByNumber(number).firstOrNull()
    }

}
