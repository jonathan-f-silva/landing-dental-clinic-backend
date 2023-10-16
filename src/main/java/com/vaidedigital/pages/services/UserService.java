package com.vaidedigital.pages.services;

import com.vaidedigital.pages.dtos.CreateUserDto;
import com.vaidedigital.pages.entities.User;
import com.vaidedigital.pages.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for the User entity.
 */
@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * Register a new user.
   *
   * @param user the user to register
   * @return the registered user
   */
  public User register(CreateUserDto user) {
    User newUser = new User(
        user.name(),
        user.email(),
        passwordEncoder.encode(user.password()));
    return userRepository.save(newUser);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
