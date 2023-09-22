package com.vaidedigital.pages;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class PagesApplicationTests {

  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  private String jsonOf(Map<String, Object> map) throws Exception {
    return objectMapper.writeValueAsString(map);
  }

  @Test
  void contextLoads() {
  }

  @Test
  @DirtiesContext
  void testAddNewPage_returnsPage() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    Map<String, Object> expectedResponse = Map.of(
        "id", 1,
        "url", "https://example.com",
        "config", "{}");

    mockMvc.perform(post("/page")
        .contentType("application/json")
        .content(jsonOf(requestBody)))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedResponse)));
  }
}
