package au.com.dius.coffee.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany

import au.com.dius.coffee.dto.UpdateOrderRequest

@Entity
class CoffeeOrder(
  @Id @GeneratedValue(strategy=IDENTITY) var number: Long = 0,
  @OneToMany(mappedBy="number") var coffees: List<Coffee> = emptyList(),
  var name: String = ""
) {

  fun patch(request: UpdateOrderRequest) =
    apply {
      if (request.name != null) {
        name = request.name
      }
    }

}
