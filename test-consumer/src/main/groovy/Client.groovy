package au.com.dius.coffee

import groovyx.net.http.RESTClient

class Client {

  private RESTClient http

  private Long orderId
  private Long coffeeId

  Client() { }

  Client(String url) {
    http = new RESTClient(url, 'application/json')
    http.handler.failure = { resp, data ->
      resp.data = data
      resp
    }
  }

  def getMenuMenu() {
    http.get(path: '/menu')
  }

  def getCoffeeMenu() {
    http.get(path: '/menu/coffee')
  }

  def listOrders() {
    http.get(path: '/order')
  }

  def getOrder() {
    http.get(path: "/order/${orderId}")
  }

  def createOrder() {
    def response = http.post(path: '/order')
    if (response.success) {
      orderId = response.data.id
    }
    response
  }

  /** Valid args for API are: name */
  def updateOrder(Map args) {
    http.patch(path: "/order/${orderId}", body: args)
  }

  def cancelOrder() {
    def response = http.delete(path: "/order/${orderId}")
    if (response.success) {
      orderId = null
      coffeeId = null
    }
    response
  }

  /** Valid args for API are: style (mandatory), size */
  def addCoffee(Map args) {
    def response = http.post(path: "/order/${orderId}/coffee", body: args)
    if (response.success) {
      coffeeId = response.data.id
    }
    response
  }

  def getCoffee() {
    http.get(path: "/order/${orderId}/coffee/${coffeeId}")
  }

  /** Valid args for API are: style, size */
  def updateCoffee(Map args) {
    http.patch(path: "/order/${orderId}/coffee/${coffeeId}", body: args)
  }

  def cancelCoffee() {
    def response = http.delete(path: "/order/${orderId}/coffee/${coffeeId}")
    if (response.success) {
      coffeeId = null
    }
    response
  }

}
