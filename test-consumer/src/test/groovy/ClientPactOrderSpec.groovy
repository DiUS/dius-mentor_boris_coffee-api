package au.com.dius.coffee

import au.com.dius.pact.consumer.PactError
import au.com.dius.pact.consumer.PactError$
import au.com.dius.pact.consumer.PactVerified$
import au.com.dius.pact.consumer.VerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification

class ClientPactOrderSpec extends Specification {

  private Client client
  private PactBuilder provider

  def setup() {
    client = new Client('http://localhost:9876')
    provider = new PactBuilder()
    provider {
      serviceConsumer 'API Test Consumer'
      hasPactWith 'Coffee Ordering Provider'
      port 9876
    }
  }

  def 'lists no orders'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to list orders'
      withAttributes path: '/order'

      willRespondWith status: 200
      withBody {
        orders([])
      }
    }

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.listOrders().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      orders: []
    ]
  }

  def 'lists many orders'() {
    given:
    provider {
      given 'many orders'

      uponReceiving 'request to list orders'
      withAttributes path: '/order'

      willRespondWith status: 200
      withBody {
        orders minLike(3, 7) {
          id integer(29)
          path ~"/order/\\d+", '/order/29'
        }
      }
    }

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.listOrders().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    for (def order in result.orders) {
      order.path == "/order/${order.id}".toString()
    }
  }

  def 'gets one order'() {
    given:
    provider {
      given 'order 23'

      uponReceiving 'request to get a specific order'
      withAttributes path: '/order/23'

      willRespondWith status: 200
      withBody {
        id 23
        coffees minLike(2, 2) {
          id integer(66)
        }
        name ~/\w+/, 'Jimothy'
        path '/order/23'
      }
    }
    client.orderId = 23

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.getOrder().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result.with {
      id == 23
      name == 'Jimothy'
      coffees.size() >= 2
      for (coffee in coffees) {
        coffee.id == 66
      }
      path == '/order/23'
    }
  }

  def 'fails to get one order'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to get a specific order'
      withAttributes path: '/order/999'

      willRespondWith status: 404
      withBody {
        message 'Order with id 999 not found'
        path '/order/999'
      }
    }
    client.orderId = 999

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.getOrder().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      message: 'Order with id 999 not found',
      path: '/order/999'
    ]
  }

  def 'names an order'() {
    given:
    provider {
      given 'empty order 19'

      uponReceiving 'request to change order name'
      withAttributes method: 'patch', path: '/order/19'
      withBody {
        name 'Jimbo'
      }

      willRespondWith status: 200
      withBody {
        id 19
        path '/order/19'
      }
    }
    client.orderId = 19

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.updateOrder(name: 'Jimbo').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      id: 19,
      path: '/order/19'
    ]
  }

  def 'fails to name an order'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to change order name'
      withAttributes method: 'patch', path: '/order/777'
      withBody {
        name 'No Face'
      }

      willRespondWith status: 404
      withBody {
        message 'Order with id 777 not found'
        path '/order/777'
      }
    }
    client.orderId = 777

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.updateOrder(name: 'No Face').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      message: 'Order with id 777 not found',
      path: '/order/777'
    ]
  }

  def 'cancels an order'() {
    given:
    provider {
      given 'empty order 19'

      uponReceiving 'request to cancel the order'
      withAttributes method: 'delete', path: '/order/19'

      willRespondWith status: 204
    }
    client.orderId = 19

    when:
    VerificationResult pactResult = provider.run {
      client.cancelOrder()
    }

    then:
    pactResult == PactVerified$.MODULE$
  }

  def 'fails to cancel an order'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to cancel the order'
      withAttributes method: 'delete', path: '/order/13'

      willRespondWith status: 404
      withBody {
        message 'Order with id 13 not found'
        path '/order/13'
      }
    }
    client.orderId = 13

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.cancelOrder().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      message: 'Order with id 13 not found',
      path: '/order/13'
    ]
  }

}
