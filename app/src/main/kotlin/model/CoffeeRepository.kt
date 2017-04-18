package au.com.dius.coffee.model

import org.springframework.data.repository.CrudRepository

interface CoffeeRepository : CrudRepository<Coffee, Long> {

  fun findOneByNumber(number: Long): Coffee?

}
