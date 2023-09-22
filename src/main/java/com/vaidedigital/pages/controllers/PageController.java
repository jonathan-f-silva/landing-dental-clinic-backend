package com.vaidedigital.pages.controllers;

import com.vaidedigital.pages.dtos.CreatePageDto;
import com.vaidedigital.pages.entities.Page;
import com.vaidedigital.pages.services.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
