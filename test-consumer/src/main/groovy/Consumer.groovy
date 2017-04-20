package au.com.dius.coffee

println ''
new Client('http://localhost:8080').with {
  [
    'create order':        { createOrder() },
    'add cap':             { addCoffee(style: 'Cappuccino') },
    'set name':            { updateOrder(name: 'Boris') },
    'add 3/4 latte':       { addCoffee(style: 'Latte', size: '3/4') },
    'get order':           { getOrder() },
    'cancel order':        { cancelOrder() }
  ].forEach { label, func ->
    def response = func()
    if (!response.success) {
      println "${label} - failed: ${response.status} ${response.data}\n"
      System.exit(1)
    }

    println "${label}: ${response.status} ${response.data}"
  }
}
println ''
