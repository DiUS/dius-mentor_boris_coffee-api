package au.com.dius.coffee.service

import au.com.dius.coffee.model.Coffee

class StubCoffeeRepo {

  companion object {

    var findOneByNumber: Coffee? = null
    var deleteByNumber: Coffee? = null

  }

}
