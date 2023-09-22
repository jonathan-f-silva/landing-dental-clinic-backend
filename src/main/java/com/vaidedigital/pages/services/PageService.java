package com.vaidedigital.pages.services;

import com.vaidedigital.pages.dtos.CreatePageDto;
import com.vaidedigital.pages.dtos.UpdatePageDto;
import com.vaidedigital.pages.entities.Page;
import com.vaidedigital.pages.repositories.PageRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for the pages API.
 */
@Service
public class PageService {
  @Autowired
  private PageRepository pageRepository;

  /**
   * Adds a new page.
   *
   * @param newPage The new page to add.
   * @return The added page.
   */
  public Page addNewPage(CreatePageDto newPage) {
    Page page = new Page(newPage.url(), newPage.config());
    return pageRepository.save(page);
  }

  /**
   * Gets a page by id.
   *
   * @param id The id of the page to get.
   * @return The page.
   */
  public Optional<Page> getPageById(Integer id) {
    return pageRepository.findById(id);
  }

  /**
   * Updates a page by id.
   *
   * @param id          The id of the page to update.
   * @param updatedPage The updated page.
   * @return The updated page.
   */
  public Page updatePageById(Integer id, UpdatePageDto updatedPage) {
    Page page = new Page(updatedPage.url(), updatedPage.config());
    page.setId(id);
    return pageRepository.save(page);
  }
}
