package ru.filippov.neatvue.config.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.core.Authentication;
import ru.filippov.neatvue.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


public interface TokenProvider {


    String generateRefreshToken(User user, long expiration);
    String generateAccessToken(User user, long expiration);
    String generateRefreshToken(Authentication authentication, long expiration);
    String generateAccessToken(Authentication authentication, long expiration);
    String generateRefreshToken(User user);
    String generateAccessToken(User user);
    String generateRefreshToken(Authentication authentication);
    String generateAccessToken(Authentication authentication);



    boolean validateToken(String authToken);

    String getUserNameFromToken(String token) throws JWTVerificationException;

    String getHeader(String token) throws JWTVerificationException;

    String getToken(HttpServletRequest request);

    long getAccessTokenExpiration();
    long getRefreshTokenExpiration();
}
