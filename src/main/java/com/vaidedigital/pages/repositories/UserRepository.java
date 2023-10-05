package com.vaidedigital.pages.repositories;

import com.vaidedigital.pages.entities.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the User entity.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
  public Optional<User> findById(Integer id);

  public Optional<User> findByEmail(String email);
}
