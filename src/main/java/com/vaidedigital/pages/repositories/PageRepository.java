package com.vaidedigital.pages.repositories;

import com.vaidedigital.pages.entities.Page;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the Page entity.
 */
public interface PageRepository extends CrudRepository<Page, Integer> {
  public Optional<Page> findById(Integer id);
}
