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

}
