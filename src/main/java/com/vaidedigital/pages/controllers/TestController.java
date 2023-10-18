package com.vaidedigital.pages.controllers;

import com.vaidedigital.pages.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for testing. (Dev profile only)
 */
@Profile("dev")
@RestController
@RequestMapping("/test")
public class TestController {

  @Autowired
  MessageService messageService;

  @GetMapping("/mail")
  public String testMail() {
    messageService.sendSimpleMessage("www.j0n4t@gmail.com", "Testing", "API mail test.");
    return "Mail sent";
  }
}
