package au.com.dius.coffee.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class CoffeeOrder(
  @Id @GeneratedValue(strategy=IDENTITY) var number: Long = 0,
  @OneToMany(mappedBy="number") var coffees: List<Coffee> = emptyList(),
  var name: String = ""
)
