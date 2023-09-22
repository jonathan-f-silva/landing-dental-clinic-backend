package com.vaidedigital.pages.controllers;

import com.vaidedigital.pages.dtos.CreatePageDto;
import com.vaidedigital.pages.entities.Page;
import com.vaidedigital.pages.services.PageService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * Controller for the pages API.
 */
@RestController
@RequestMapping("/page")
public class PageController {

  @Autowired
  private PageService pageService;

  @PostMapping
  public Page addNewPage(@RequestBody CreatePageDto newPage) {
    return pageService.addNewPage(newPage);
  }

  /**
   * Get a page by its id.
   */
  @GetMapping("/{id}")
  public Page getPageById(@PathVariable Integer id) {
    Optional<Page> page = pageService.getPageById(id);
    if (page.isPresent()) {
      return page.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Page with id " + id + " not found");
    }
  }
}
