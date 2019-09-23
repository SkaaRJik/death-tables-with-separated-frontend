package ru.filippov.neatvue.config.jwt;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


public interface TokenProvider {

     String generateAccessToken(AuthData auth);

     String generateAccessToken(AuthData auth, long tokenExpiration);

     String generateRefreshToken(AuthData auth);

     String generateRefreshToken(AuthData auth, long tokenExpiration);

    String extractAccessTokenFromHeader(HttpServletRequest request);

    long getJwtAccessExpiration();

    long getJwtRefreshExpiration();

    String validateRefreshToken(String token);

    Map<String, Object> extractAccessTokenDataFromRequest(HttpServletRequest request);

    Authentication getAuthentication(HttpServletRequest request);
}
