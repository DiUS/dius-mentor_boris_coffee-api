package au.com.dius.coffee.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Coffee(
  @Id @GeneratedValue(strategy=GenerationType.AUTO) var number: Long = 0,
  var style: CoffeeStyle = CoffeeStyle.MAGIC,
  var size: CoffeeSize = CoffeeSize.REGULAR
)
