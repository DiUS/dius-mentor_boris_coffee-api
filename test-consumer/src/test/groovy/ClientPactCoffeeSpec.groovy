package au.com.dius.coffee

import au.com.dius.pact.consumer.PactError
import au.com.dius.pact.consumer.PactError$
import au.com.dius.pact.consumer.PactVerified$
import au.com.dius.pact.consumer.VerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification

class ClientPactCoffeeSpec extends Specification {

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

  def 'adds a coffee'() {
    given:
    provider {
      given 'empty order 19'

      uponReceiving 'request to add a latte'
      withAttributes method: 'post', path: '/order/19/coffee'
      withBody {
        style 'Magic'
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
      result = client.addCoffee(style: 'Magic').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result.path == "/order/19/coffee/${result.id}".toString()
  }

}
