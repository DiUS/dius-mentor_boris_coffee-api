package au.com.dius.coffee

import groovyx.net.http.RESTClient
import spock.lang.Specification

class ClientSpec extends Specification {

  private Client client
  private RESTClient mockHttp

  def setup() {
    mockHttp = Mock(RESTClient)
    client = new Client(http: mockHttp)
  }

  def 'process json response for valid request to list orders'() {
    given:
    def json = [
      orders: []
    ]

    when:
    def result = client.listOrders().data

    then:
    1 * mockHttp.get(_) >> [ data: json, success: true ]
    result == json
  }

  def 'process json response for valid request to add a coffee'() {
    given:
    def json = [
      id: 19,
      path: '/order/3/coffee/19'
    ]
    client.orderId = 3

    when:
    def result = client.addCoffee(style: 'Cappuccino').data

    then:
    1 * mockHttp.post(_) >> [ data: json, success: true ]
    result == json
  }

  def 'process json response for valid request to name an order'() {
    given:
    def json = [
      id: 7,
      path: '/order/7'
    ]
    client.orderId = 7

    when:
    def result = client.updateOrder(name: 'Fred').data

    then:
    1 * mockHttp.patch(_) >> [ data: json, success: true ]
    result == json
  }

}
