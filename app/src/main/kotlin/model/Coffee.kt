package au.com.dius.coffee.model

import javax.persistence.*
import javax.persistence.GenerationType.IDENTITY

@Entity
class Coffee(
  @Id @GeneratedValue(strategy=IDENTITY) var number: Long = 0,
  @ManyToOne @JoinColumn(name="order_number") var parent: CoffeeOrder = CoffeeOrder(),
  var style: CoffeeStyle = CoffeeStyle.MAGIC,
  var size: CoffeeSize = CoffeeSize.REGULAR
) {

  fun patch(newStyle: CoffeeStyle?, newSize: CoffeeSize?) =
    apply {
      if (newStyle != null) {
        style = newStyle
      }
      if (newSize != null) {
        size = newSize
      }
    }

}
