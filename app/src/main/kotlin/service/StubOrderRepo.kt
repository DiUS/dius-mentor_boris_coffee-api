package au.com.dius.coffee.service

import au.com.dius.coffee.model.CoffeeOrder

class StubOrderRepo {

  companion object {

    var findAll: Iterable<CoffeeOrder> = emptyList<CoffeeOrder>().asIterable()
    var findOneByNumber: CoffeeOrder? = null
    var deleteByNumber: CoffeeOrder? = null

  }

}
