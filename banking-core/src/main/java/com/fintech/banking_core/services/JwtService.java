package com.fintech.banking_core.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    String extractUsername(String token);

    String extractRole(String token);

    String generateToken(String email);

    Boolean isTokenValid(String token, UserDetails userDetails);

    Date getExpiration(String token);
}
