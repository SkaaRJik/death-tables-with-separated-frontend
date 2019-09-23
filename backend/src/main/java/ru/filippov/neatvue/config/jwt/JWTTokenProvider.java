package ru.filippov.neatvue.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import ru.filippov.neatvue.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class JWTTokenProvider implements TokenProvider{

    private  String JWT_SECRET;

    private  long JWT_ACCESS_EXPIRATION;

    private  long JWT_REFRESH_EXPIRATION;

    private  String JWT_ACCESS_PREFIX;

    private  String JWT_REFRESH_PREFIX;

    private  String JWT_ACCESS_HEADER;

    private  String JWT_REFRESH_HEADER;

    @Value("${app.jwt.secret}")
    public  void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }
    @Value("${app.jwt.token.access.expiration}")
    public  void setJwtAccessExpiration(long jwtAccessExpiration) {
        JWT_ACCESS_EXPIRATION = jwtAccessExpiration;
    }
    @Value("${app.jwt.token.refresh.expiration}")
    public  void setJwtRefreshExpiration(long jwtRefreshExpiration) {
        JWT_REFRESH_EXPIRATION = jwtRefreshExpiration;
    }
    @Value("${app.jwt.token.access.prefix}")
    public  void setJwtPrefix(String jwtPrefix) {
        JWT_ACCESS_PREFIX = jwtPrefix;
    }
    @Value("${app.jwt.token.refresh.prefix}")
    public void setJwtRefreshPrefix(String jwtPrefix) {
        JWT_REFRESH_PREFIX = jwtPrefix;
    }
    @Value("${app.jwt.token.access.header}")
    public  void setJwtAccessHeader(String jwtHeader) {
        JWT_ACCESS_HEADER = jwtHeader;
    }
    @Value("${app.jwt.token.refresh.header}")
    public  void setJwtRefreshHeader(String jwtHeader) {
        JWT_REFRESH_HEADER = jwtHeader;
    }


    public JWTTokenProvider() {}

    @Override
    public String generateAccessToken(AuthData auth){
        return generateAccessToken(auth, JWT_ACCESS_EXPIRATION);
    }
    @Override
    public String generateAccessToken(AuthData auth, long tokenExpiration){

        JwtBuilder jwtBuilder = createCommonPartOfJWTToken(auth, tokenExpiration);

        return JWT_ACCESS_PREFIX+jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }
    @Override
    public String generateRefreshToken(AuthData auth){
        return generateRefreshToken(auth, JWT_ACCESS_EXPIRATION);
    }
    @Override
    public String generateRefreshToken(AuthData auth, long tokenExpiration){

        JwtBuilder jwtBuilder = createCommonPartOfJWTToken(auth, tokenExpiration);

        return JWT_REFRESH_PREFIX+jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    /*public String generateToken(AuthData auth, TYPE type, long tokenExpiration) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(auth.getUsername());

        String prefix = "";
        if(type== TYPE.ACCESS_TOKEN) {
            jwtBuilder.claim("authorities", authorities);
            prefix = JWT_ACCESS_PREFIX;

        } else if(type== TYPE.REFRESH_TOKEN){
            jwtBuilder.claim("authorities", authorities);
            prefix=JWT_REFRESH_PREFIX;
        }

        jwtBuilder.setExpiration(new Date()).setExpiration(new Date(System.currentTimeMillis() + tokenExpiration));



        return prefix+jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }*/

    protected JwtBuilder createCommonPartOfJWTToken(AuthData auth, long tokenExpiration){
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(auth.getUsername());

        jwtBuilder.setExpiration(new Date()).setExpiration(new Date(System.currentTimeMillis() + tokenExpiration));
        jwtBuilder.claim("authorities", authorities);
        return jwtBuilder;
    }

    private Map<String, Object> extractDataFromToken(String token){


        if (token != null) {

            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();


            String userName = claims.getSubject();

            if (userName == null) {
                return null;
            }
            Map<String, Object> tokenData = new HashMap<>(claims.values().size());
            tokenData.entrySet().addAll(claims.entrySet());
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("authorities").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());
            tokenData.replace("authorities", authorities);
        }
        return null;
    }

    @Override
    public Map<String, Object> extractAccessTokenDataFromRequest(HttpServletRequest request) {

        String token = extractAccessTokenFromHeader(request);
        return extractDataFromToken(token);
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = extractAccessTokenFromHeader(request);
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            String userName = claims.getSubject();
            if(userName == null) {
                return null;
            }


            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("authorities").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());


            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, null, authorities);
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return usernamePasswordAuthenticationToken;
        }
        return null;
    }


    @Override
    public String extractAccessTokenFromHeader(HttpServletRequest request){

        String token = request.getHeader(JWT_ACCESS_HEADER);
        if(token == null) return null;

        return token.substring(JWT_ACCESS_PREFIX.length());
    }


    @Override
    public long getJwtAccessExpiration() {
        return JWT_ACCESS_EXPIRATION;
    }

    @Override
    public long getJwtRefreshExpiration() {
        return JWT_REFRESH_EXPIRATION;
    }

    @Override
    public String validateRefreshToken(String token) {

        String proccessedToken = token.substring(JWT_REFRESH_PREFIX.length());
        Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(proccessedToken)
                .getBody();
        return proccessedToken;

    }
}