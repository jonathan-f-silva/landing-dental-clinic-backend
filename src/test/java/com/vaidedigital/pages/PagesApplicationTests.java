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
  void testLoginUser_returnsToken() throws Exception {
    String requestBody = jsonOf(Map.of(
        "username", "sarah1",
        "password", "abc123"));

    String expectedResponse = jsonOf(Map.of(
        "token", "some-nice-jwt-token"));

    mockMvc.perform(post("/login")
        .contentType("application/json")
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().json(expectedResponse));
  }

  @Test
  void testAddNewPage_withAdminToken_createsPage() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    String token = "Bearer token-admin-token";

    Map<String, Object> expectedResponse = Map.of(
        "id", 1,
        "url", "https://example.com",
        "config", "{}");

    mockMvc.perform(post("/page")
        .header("Authorization", token)
        .contentType("application/json")
        .content(jsonOf(requestBody)))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedResponse)));

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedResponse)));
  }

  @Test
  void testAddNewPage_withUserToken_gives403Error() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    String token = "Bearer token-user-token";

    mockMvc.perform(post("/page")
        .header("Authorization", token)
        .contentType("application/json")
        .content(jsonOf(requestBody)))
        .andExpect(status().isForbidden());

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testAddNewPage_withoutToken_gives401Error() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    mockMvc.perform(post("/page")
        .contentType("application/json")
        .content(jsonOf(requestBody)))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testUpdatePageById_withValidToken_updatesPage() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    String adminToken = "Bearer token-admin-token";
    String userToken = "Bearer token-user-token";

    Map<String, Object> expectedResponse = Map.of(
        "id", 1,
        "url", "https://example.com",
        "config", "{}");

    mockMvc.perform(post("/page")
        .header("Authorization", adminToken)
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
        .header("Authorization", userToken)
        .contentType("application/json")
        .content(jsonOf(updateRequestBody)))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedUpdateResponse)));

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedUpdateResponse)));
  }

  @Test
  void testUpdatePage_withoutToken_gives401Error() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    String adminToken = "Bearer token-admin-token";

    Map<String, Object> expectedResponse = Map.of(
        "id", 1,
        "url", "https://example.com",
        "config", "{}");

    mockMvc.perform(post("/page")
        .header("Authorization", adminToken)
        .contentType("application/json")
        .content(jsonOf(requestBody)))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedResponse)));

    Map<String, Object> updateRequestBody = Map.of(
        "url", "https://example.com",
        "config", "{\"title\": \"Example\"}");

    mockMvc.perform(put("/page/1")
        .contentType("application/json")
        .content(jsonOf(updateRequestBody)))
        .andExpect(status().isUnauthorized());

    mockMvc.perform(get("/page/1"))
        .andExpect(status().isOk())
        .andExpect(content().json(jsonOf(expectedResponse)));
  }
}
