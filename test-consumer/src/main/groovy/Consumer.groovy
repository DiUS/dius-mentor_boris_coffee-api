package au.com.dius.coffee

println ''
new Client('http://localhost:8080').with {
  [
    'create order':        { createOrder() },
    'add cap':             { addCoffee(style: 'Cappuccino') },
    'get coffee':          { getCoffee() },
    'set name':            { updateOrder(name: 'Boris') },
    'cancel first coffee': { cancelCoffee() },
    'add 3/4 latte':       { addCoffee(style: 'Latte', size: '3/4') },
    'get order':           { getOrder() },
    'cancel order':        { cancelOrder() },
    'menu':                { getMenuMenu() },
    'coffee menu':         { getCoffeeMenu() }
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
