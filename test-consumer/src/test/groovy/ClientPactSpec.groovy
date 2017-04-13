package au.com.dius.coffee

import au.com.dius.pact.consumer.PactError
import au.com.dius.pact.consumer.PactError$
import au.com.dius.pact.consumer.PactVerified$
import au.com.dius.pact.consumer.VerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification

class ClientPactSpec extends Specification {

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

  def 'adds a coffee'() {
    given:
    provider {
      given 'empty order 19'

      uponReceiving 'request to add a latte'
      withAttributes method: 'post', path: '/order/19'
      withBody {
        type 'Cafe Latte'
      }

      willRespondWith status: 201
      withBody {
        id integer(37)
        path ~"/order/\\d+/coffee/\\d+", '/order/19/coffee/37'
      }
    }
    client.orderId = 19

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.addCoffee('Cafe Latte').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result.path == "/order/19/coffee/${result.id}".toString()
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

}
