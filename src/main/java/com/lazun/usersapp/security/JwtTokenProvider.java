package com.lazun.usersapp.security;

import com.lazun.usersapp.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${app.jwt-secret}")
  private String jwtSecret;

  @Value("${app.jwt-expiration-milliseconds}")
  private int jwtExpirationInMs;

  public String buildToken(Authentication authentication) {
    String username = authentication.getName();
    Date dateNow = new Date();
    Date expirationDate = new Date(dateNow.getTime() + jwtExpirationInMs);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(expirationDate)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserName(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
      return true;
    } catch (SignatureException ex) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST.name(), "Not valid signature", HttpStatus.BAD_REQUEST.value());
    } catch (MalformedJwtException ex) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST.name(), "Not valid token", HttpStatus.BAD_REQUEST.value());

    } catch (ExpiredJwtException ex) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST.name(), "Expired token", HttpStatus.BAD_REQUEST.value());

    } catch (UnsupportedJwtException ex) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST.name(), "Not compatible token", HttpStatus.BAD_REQUEST.value());

    } catch (IllegalArgumentException ex) {
      throw new ApiException(
          HttpStatus.BAD_REQUEST.name(), "Token empty", HttpStatus.BAD_REQUEST.value());
    }
  }
}
