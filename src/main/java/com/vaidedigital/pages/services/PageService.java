package com.vaidedigital.pages.services;

import com.vaidedigital.pages.dtos.CreatePageDto;
import com.vaidedigital.pages.entities.Page;
import com.vaidedigital.pages.repositories.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for the pages API.
 */
@Service
public class PageService {
  @Autowired
  private PageRepository pageRepository;

  public Page addNewPage(CreatePageDto newPage) {
    Page page = new Page(newPage.url(), newPage.config());
    return pageRepository.save(page);
  }
}
