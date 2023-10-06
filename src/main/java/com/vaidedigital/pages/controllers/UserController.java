package com.vaidedigital.pages.controllers;

import com.vaidedigital.pages.dtos.CreateUserDto;
import com.vaidedigital.pages.entities.User;
import com.vaidedigital.pages.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
