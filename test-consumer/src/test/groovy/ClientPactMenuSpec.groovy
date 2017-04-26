package au.com.dius.coffee

import au.com.dius.pact.consumer.PactError
import au.com.dius.pact.consumer.PactError$
import au.com.dius.pact.consumer.PactVerified$
import au.com.dius.pact.consumer.VerificationResult
import au.com.dius.pact.consumer.groovy.PactBuilder
import spock.lang.Specification

class ClientPactMenuSpec extends Specification {

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

  def 'gets the base menu'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to fetch the menu'
      withAttributes method: 'get', path: '/menu'

      willRespondWith status: 200
      withBody {
        coffee '/menu/coffee'
        path '/menu'
      }
    }

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.getMenuMenu().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      coffee: '/menu/coffee',
      path: '/menu'
    ]
  }

  def 'gets the coffee menu'() {
    given:
    provider {
      given 'no orders'

      uponReceiving 'request to fetch the coffee menu'
      withAttributes method: 'get', path: '/menu/coffee'

      willRespondWith status: 200
      withBody {
        style eachLike(7, regexp(~/.+/, 'Latte'))
        size eachLike(5, regexp(~/.+/, 'Regular'))
        path '/menu/coffee'
      }
    }

    when:
    def result
    VerificationResult pactResult = provider.run {
      result = client.getCoffeeMenu().data
    }

    then:
    pactResult == PactVerified$.MODULE$
    result == [
      style: ['Latte'] * 7,
      size: ['Regular'] * 5,
      path: '/menu/coffee'
    ]
  }

}
