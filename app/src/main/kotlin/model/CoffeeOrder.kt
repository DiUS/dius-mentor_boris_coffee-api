package au.com.dius.coffee.model

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

import au.com.dius.coffee.dto.UpdateOrderRequest

@Entity
class CoffeeOrder(
  @Id @GeneratedValue(strategy=IDENTITY) var number: Long = 0,
  @OneToMany(mappedBy="parent", cascade=arrayOf(CascadeType.ALL)) var coffees: MutableList<Coffee> = mutableListOf(),
  var name: String = ""
) {

  fun patch(request: UpdateOrderRequest) =
    apply {
      if (request.name != null) {
        name = request.name
      }
    }

}
