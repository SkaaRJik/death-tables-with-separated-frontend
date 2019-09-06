package ru.filippov.neatvue.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.service.user.UserPrinciple;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Component
@Slf4j
public class JwtProvider implements TokenProvider {



    @Value("${app.jwt.Secret}")
    private String JWT_SECRET;

    @Value("${app.jwt.Access_Expiration}")
    private long JWT_ACCESS_EXPIRATION;

    @Value("${app.jwt.Refresh_Expiration}")
    private long JWT_REFRESH_EXPIRATION;

    @Value("${app.jwt.prefix.access}")
    private String JWT_PREFIX;

    @Value("${app.jwt.Header}")
    private String JWT_HEADER;

    @Override
    public String generateAccessToken(Authentication authentication) {
        return generateAccessToken(authentication, this.JWT_ACCESS_EXPIRATION);
    }

    @Override
    public String generateAccessToken(Authentication authentication, long expirationTime) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>(1);
        claims.put("role", authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

       /* String token = JWT_PREFIX + JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withArrayClaim("role", authentication
                        .getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toArray(String[]::new)
                )
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(HMAC512(JWT_SECRET.getBytes()));*/

        return generateToken(userPrincipal.getUsername(), claims, expirationTime);
    }

    @Override
    public String generateAccessToken(User user) {
        return generateAccessToken(user, this.JWT_ACCESS_EXPIRATION);
    }

    @Override
    public String generateAccessToken(User user, long expirationTime) {

        Map<String, Object> claims = new HashMap<>(1);
        claims.put("role", user.getRoles()
                .stream()
                .map(role -> role.name())
                .collect(Collectors.toList()));

        return generateToken(user.getUsername(), claims, expirationTime);
    }


    @Override
    public String generateRefreshToken(Authentication authentication) {
        return generateRefreshToken(authentication, this.JWT_REFRESH_EXPIRATION);
    }

    @Override
    public String generateRefreshToken(Authentication authentication, long expirationTime) {

        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>(0);

        return generateToken(userPrincipal.getUsername(), claims, expirationTime);
    }

    @Override
    public String generateRefreshToken(User user) {
        return generateRefreshToken(user, this.JWT_REFRESH_EXPIRATION);
    }

    @Override
    public String generateRefreshToken(User user, long expirationTime) {

        Map<String, Object> claims = new HashMap<>(0);

        return generateToken(user.getUsername(), claims, expirationTime);
    }


    private String generateToken(
            String username,
            Map<String, Object> claims,
            long expirationTime
    ) {

        JWTCreator.Builder builder = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime));

        claims.forEach((key, value) -> {
            if(value instanceof List){
                builder.withArrayClaim(key, ((List<String>) value).toArray(String[]::new));
            } else {
                builder.withClaim(key, value.toString());
            }
        });
        return JWT_PREFIX + builder.sign(HMAC512(JWT_SECRET.getBytes()));
    }

    @Override
    public boolean validateToken(String token) {


        try{
            JWT.require(HMAC512(JWT_SECRET.getBytes()))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e){
            e.printStackTrace();
        }

        
        return false;
    }

    @Override
    public String getUserNameFromToken(String token) throws JWTVerificationException {
        return JWT.require(HMAC512(JWT_SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();
    }

    @Override
    public String getHeader(String token) throws JWTVerificationException {
        DecodedJWT verify = JWT.require(HMAC512(JWT_SECRET.getBytes()))
                .build()
                .verify(token);

        return verify.getHeader();

    }

    @Override
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(JWT_HEADER);

        if (authHeader != null && authHeader.startsWith(JWT_PREFIX)) {
            return authHeader.substring(JWT_PREFIX.length());
        }

        return null;
    }

    @Override
    public long getAccessTokenExpiration() {
        return this.JWT_ACCESS_EXPIRATION;
    }

    @Override
    public long getRefreshTokenExpiration() {
        return this.JWT_REFRESH_EXPIRATION;
    }
}