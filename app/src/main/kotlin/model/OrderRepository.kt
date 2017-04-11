package au.com.dius.coffee.model

import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface OrderRepository : CrudRepository<CoffeeOrder, Long> {

  fun findOneByNumber(number: Long): CoffeeOrder?

  @Transactional
  fun deleteByNumber(number: Long): List<CoffeeOrder>

}
