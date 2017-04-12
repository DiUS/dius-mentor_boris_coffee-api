package au.com.dius.coffee

println ''
new Client('http://localhost:8080').with {
  [
    'create order': { createOrder() }
  ].forEach { label, func ->
    def response = func()
    if (!response.success) {
      println "${label}: failed\n"
      System.exit(1)
    }

    println "${label}: ${response.status} ${response.data}"
  }
}
println ''
