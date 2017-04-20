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

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class CoffeeEndpointsTest {

  @Test
  fun `add valid coffee`() {
    post("/order/1/coffee", """{"style":"Short Macchiatto"}""")
      .andExpect(status().isCreated())
  }

  @Test
  fun `add valid coffee to invalid order`() {
    val coffeeId = 321
    post("/order/${coffeeId}/coffee", """{"style":"Magic"}""")
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.path", equalTo("/order/${coffeeId}/coffee")))
      .andExpect(jsonPath("$.message", equalTo("Order with id ${coffeeId} not found")))
  }

  @Test
  fun `add invalid coffee`() {
    post("/order/1/coffee", """{"style":"Chai Latte"}""")
      .andExpect(status().isBadRequest())
  }

  @Autowired lateinit var mvc: MockMvc

  fun perform(builder: MockHttpServletRequestBuilder) =
    mvc.perform(
      builder.accept(MediaType.APPLICATION_JSON)
    )

  fun post(path: String, content: String) =
    perform(MockMvcRequestBuilders
      .post(path)
      .contentType(MediaType.APPLICATION_JSON)
      .content(content.toByteArray())
    )

}
