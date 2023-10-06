package com.vaidedigital.pages.services;

import com.vaidedigital.pages.dtos.LoginDto;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

/**
 * Service for the Authentication tasks.
 */
@Service
public class AuthService {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtEncoder jwtEncoder;

  /**
   * Get an {@link Authentication} object for the given {@link LoginDto}.
   *
   * @param loginDto the authentication data
   * @return an authentication object
   */
  public Authentication getAuth(LoginDto loginDto) {
    return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.email(),
        loginDto.password()));
  }

  /**
   * Generate a JWT token for the given {@link Authentication}.
   *
   * @param auth the authentication data
   * @return a JWT token
   */
  public String getToken(Authentication auth) {
    Instant now = Instant.now();
    long expiry = 36000L;
    String scope = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(" "));
    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("vaidedigital-pages")
        .issuedAt(now)
        .expiresAt(now.plusSeconds(expiry))
        .subject(auth.getName())
        .claim("scope", scope)
        .build();
    return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
  }

}
