package au.com.dius.coffee.model

import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional

interface CoffeeRepository : CrudRepository<Coffee, Long> {

  fun findOneByNumber(number: Long): Coffee?

  @Transactional
  fun deleteByNumber(number: Long): List<Coffee>

}
