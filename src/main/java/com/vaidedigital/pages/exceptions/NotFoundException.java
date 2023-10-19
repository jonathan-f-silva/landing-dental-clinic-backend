package com.vaidedigital.pages.exceptions;

/**
 * Exception thrown when something is not found.
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
