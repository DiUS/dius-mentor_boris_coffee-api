package au.com.dius.coffee

import groovyx.net.http.RESTClient

class Client {

  private RESTClient http

  private Long orderId
  private Long coffeeId

  Client() { }

  Client(String url) {
    http = new RESTClient(url)
    http.handler.failure = { resp, data -> resp }
  }

  def listOrders() {
    http.get(path: '/order')
  }

  def createOrder() {
    def response = http.post(path: '/order')
    if (response.success) {
      orderId = response.data.id
    }
    response
  }

  def addCoffee(String type) {
    def response = http.post(
      path: "/order/${orderId}",
      requestContentType: 'application/json',
      body: [ type: type ]
    )
    if (response.success) {
      coffeeId = response.data.id
    }
    response
  }

  def updateOrder(Map args) {
    def response = http.put(path: "/order/${orderId}/coffee/${coffeeId}", body: args)
    if (response.success) {
      coffeeId = response.data.id
    }
    response
  }

}
