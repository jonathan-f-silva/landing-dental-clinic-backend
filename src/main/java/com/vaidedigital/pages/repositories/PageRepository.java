package com.vaidedigital.pages.repositories;

import com.vaidedigital.pages.entities.Page;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for the Page entity.
 */
public interface PageRepository extends CrudRepository<Page, Integer> {
}
