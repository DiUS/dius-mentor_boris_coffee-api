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

      uponReceiving 'request to add a magic'
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

  def 'fails to add a coffee with invalid style'() {
    given:
    provider {
      given 'empty order 19'

      uponReceiving 'request to add a large braggadoccio'
      withAttributes method: 'post', path: '/order/19/coffee'
      withBody {
        style 'Braggadoccio'
        size 'Large'
      }

      willRespondWith status: 400
      withBody {
        message 'Braggadoccio is not a recognised style'
        path '/order/19/coffee'
      }
    }
    client.orderId = 19

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.addCoffee(style: 'Braggadoccio', size: 'Large').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      message: 'Braggadoccio is not a recognised style',
      path: '/order/19/coffee'
    ]
  }

  def 'fails to add a coffee with invalid size'() {
    given:
    provider {
      given 'empty order 19'

      uponReceiving 'request to add a tinycino'
      withAttributes method: 'post', path: '/order/19/coffee'
      withBody {
        style 'Cappuccino'
        size 'Tiny'
      }

      willRespondWith status: 400
      withBody {
        message 'Tiny is not a recognised size'
        path '/order/19/coffee'
      }
    }
    client.orderId = 19

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.addCoffee(style: 'Cappuccino', size: 'Tiny').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      message: 'Tiny is not a recognised size',
      path: '/order/19/coffee'
    ]
  }

  def 'fails to add a coffee to non-existent order'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to add a cappuccino'
      withAttributes method: 'post', path: '/order/55/coffee'
      withBody {
        style 'Cappuccino'
      }

      willRespondWith status: 404
      withBody {
        message 'Order with id 55 not found'
        path '/order/55/coffee'
      }
    }
    client.orderId = 55

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.addCoffee(style: 'Cappuccino').data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      message: 'Order with id 55 not found',
      path: '/order/55/coffee'
    ]
  }

}
