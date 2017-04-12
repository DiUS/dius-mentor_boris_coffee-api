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

  def 'process json for valid request to list coffees'() {
    given:
    def json = [
      orders: []
    ]

    when:
    def result = client.listOrders().data

    then:
    1 * mockHttp.get(_) >> [ data: json, success: true ]
    result == [
      orders: []
    ]
  }

}
