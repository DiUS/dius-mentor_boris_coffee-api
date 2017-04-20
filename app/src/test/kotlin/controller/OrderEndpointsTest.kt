package au.com.dius.coffee.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.hasSize

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderEndpointsTest {

  @Test
  fun `list orders`() {
    get("/order")
      .andExpect(status().isOk())
      .andExpect(jsonPath<MutableCollection<out Int>>("$.orders", hasSize(greaterThan(0))))
  }

  @Test
  fun `create order`() {
    post("/order").andExpect(status().isCreated())
  }

  @Test
  fun `get extant order`() {
    get("/order/1").andExpect(status().isOk())
  }

  @Test
  fun `get non-existent order`() {
    get("/order/24601").andExpect(status().isNotFound())
  }

  @Test
  fun `delete extant order`() {
    val orderId = 2
    delete("/order/${orderId}")
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", equalTo(orderId)))
      .andExpect(jsonPath("$.path", equalTo("/order/${orderId}")))
  }

  @Test
  fun `delete non-existent order`() {
    val orderId = 321
    delete("/order/${orderId}")
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.path", equalTo("/order/${orderId}")))
      .andExpect(jsonPath("$.message", equalTo("Order with id ${orderId} not found")))
  }

  @Autowired lateinit var mvc: MockMvc

  fun perform(builder: MockHttpServletRequestBuilder) =
    mvc.perform(
      builder.accept(MediaType.APPLICATION_JSON)
    )

  fun get(path: String) =
    perform(MockMvcRequestBuilders.get(path))

  fun post(path: String) =
    perform(MockMvcRequestBuilders.post(path))

  fun delete(path: String) =
    perform(MockMvcRequestBuilders.delete(path))

}
