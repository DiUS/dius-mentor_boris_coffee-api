package au.com.dius.coffee

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

import au.com.dius.coffee.model.OrderRepository
import au.com.dius.coffee.model.CoffeeOrder

@SpringBootApplication
open class Application {

  @Bean
  open fun test(repo: OrderRepository) = CommandLineRunner {
    repo.save(CoffeeOrder(name="Boris"))
    repo.save(CoffeeOrder(name="Delete Me"))
  }

}

fun main(args: Array<String>) {
  SpringApplication.run(Application::class.java, *args)
}
