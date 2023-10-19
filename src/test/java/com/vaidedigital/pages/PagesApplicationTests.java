package com.vaidedigital.pages;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaidedigital.pages.entities.User;
import com.vaidedigital.pages.repositories.UserRepository;
import com.vaidedigital.pages.security.Role;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PagesApplicationTests {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  UserRepository userRepository;

  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  ObjectMapper objectMapper = new ObjectMapper();

  User mockAdminUser = new User(
      "Sarah", "sarah1@mail.com", passwordEncoder.encode("abc123"), Role.ROLE_ADMIN);

  User mockUser = new User(
      "Joe", "joe1", passwordEncoder.encode("abc123"), Role.ROLE_USER);

  String jsonOf(Map<String, Object> map) throws Exception {
    return objectMapper.writeValueAsString(map);
  }

  String getTokenFromMockUser(String email) throws Exception {
    String requestBody = jsonOf(Map.of(
        "email", email,
        "password", "abc123"));

    MvcResult result = mockMvc.perform(post("/login")
        .contentType("application/json")
        .content(requestBody))
        .andReturn();

    return "Bearer " + result.getResponse().getContentAsString();
  }

  @BeforeEach
  void populateDatabase() {
    userRepository.save(mockAdminUser);
    userRepository.save(mockUser);
  }

  @Test
  void contextLoads() {
  }

  @Test
  void testLoginUser_returnsToken() throws Exception {
    String requestBody = jsonOf(Map.of(
        "email", mockAdminUser.getEmail(),
        "password", "abc123"));

    mockMvc.perform(post("/login")
        .contentType("application/json")
        .content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void testAddNewPage_withAdminToken_createsPage() throws Exception {
    Map<String, Object> requestBody = Map.of(
        "url", "https://example.com",
        "config", "{}");

    String token = getTokenFromMockUser(mockAdminUser.getEmail());

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

    String token = getTokenFromMockUser(mockUser.getEmail());

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

    String adminToken = getTokenFromMockUser(mockAdminUser.getEmail());
    String userToken = getTokenFromMockUser(mockUser.getEmail());

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

    String adminToken = getTokenFromMockUser(mockAdminUser.getEmail());

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
