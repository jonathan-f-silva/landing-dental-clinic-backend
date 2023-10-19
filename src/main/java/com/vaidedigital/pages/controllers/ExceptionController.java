package com.vaidedigital.pages.controllers;

import com.vaidedigital.pages.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Controller for the exceptions.
 */
@RestControllerAdvice
public class ExceptionController {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public String handleNotFound(NotFoundException e) {
    return e.getMessage();
  }
}
