package com.vaidedigital.pages.dtos;

/**
 * A data transfer object representing the information needed to create a new
 * landing page.
 * Contains the URL and configuration data for the page.
 */
public record CreateUserDto(String name, String email, String password) {
}
