package com.vaidedigital.pages.services;

import com.vaidedigital.pages.dtos.CreatePageDto;
import com.vaidedigital.pages.entities.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PageServiceTest {
  @Autowired
  private PageService pageService;

  @Test
  @DirtiesContext
  public void testAddNewPage() {
    CreatePageDto newPage = new CreatePageDto("https://example.com", "{}");
    Page page = new Page(newPage.url(), newPage.config());

    Page result = pageService.addNewPage(newPage);

    assertEquals(page.getUrl(), result.getUrl());
    assertEquals(page.getConfig(), result.getConfig());
    assertNotNull(result.getId());
  }
}
