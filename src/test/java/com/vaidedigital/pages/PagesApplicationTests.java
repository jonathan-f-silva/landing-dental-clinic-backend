package com.vaidedigital.pages;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
  void testAddNewPage_createsPage() throws Exception {
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

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedResponse)));
  }

  @Test
  void testUpdatePageById_updatesPage() throws Exception {
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

    Map<String, Object> updateRequestBody = Map.of(
        "url", "https://example.com",
        "config", "{\"title\": \"Example\"}");

    Map<String, Object> expectedUpdateResponse = Map.of(
        "id", 1,
        "url", "https://example.com",
        "config", "{\"title\": \"Example\"}");

    mockMvc.perform(put("/page/1")
        .contentType("application/json")
        .content(jsonOf(updateRequestBody)))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedUpdateResponse)));

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedUpdateResponse)));
  }
}
