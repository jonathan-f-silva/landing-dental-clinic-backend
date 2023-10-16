package com.vaidedigital.pages.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * Messaging Service.
 */
@Component
public class MessageService {

  @Value("${app.mail.from}")
  String from;

  @Autowired
  private JavaMailSender emailSender;

  /**
   * Send Simple Email.
   */
  public void sendSimpleMessage(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    emailSender.send(message);
  }
}
