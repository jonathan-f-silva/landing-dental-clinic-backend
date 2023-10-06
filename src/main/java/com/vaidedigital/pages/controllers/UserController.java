package com.vaidedigital.pages.controllers;

import com.vaidedigital.pages.dtos.CreateUserDto;
import com.vaidedigital.pages.dtos.LoginDto;
import com.vaidedigital.pages.entities.User;
import com.vaidedigital.pages.services.AuthService;
import com.vaidedigital.pages.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the User entity.
 */
@RestController
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  AuthService authService;

  /**
   * Generate a token for the given {@link Authentication}.
   *
   * @param loginDto the authentication data
   * @return a token
   */
  @PostMapping("/login")
  public String login(@RequestBody LoginDto loginDto) {
    Authentication auth = authService.getAuth(loginDto);
    return authService.getToken(auth);
  }

  /**
   * Register a new user.
   *
   * @param user the user to register
   * @return the registered user
   */
  @PostMapping("/register")
  public User register(@RequestBody CreateUserDto user) {
    return userService.register(user);
  }
}
