package au.com.dius.coffee.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import au.com.dius.coffee.dto.CoffeeMenuResponse
import au.com.dius.coffee.dto.MenuResponse

@CrossOrigin
@RestController
@RequestMapping("/menu")
class MenuEndpoints {

  @GetMapping
  fun menu(): ResponseEntity<Any> =
    ResponseEntity(MenuResponse.default(), HttpStatus.OK)

  @GetMapping("/coffee")
  fun coffeeMenu(): ResponseEntity<Any> =
    ResponseEntity(CoffeeMenuResponse.default(), HttpStatus.OK)

}
