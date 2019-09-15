package ru.filippov.neatvue.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import ru.filippov.neatvue.domain.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenAuthenticationHelper {

    private static String JWT_SECRET;

    private static long JWT_ACCESS_EXPIRATION;

    private static long JWT_REFRESH_EXPIRATION;

    private static String JWT_ACCESS_PREFIX;

    private static String JWT_REFRESH_PREFIX;


    private static String JWT_ACCESS_HEADER;

    private static String JWT_REFRESH_HEADER;

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



    public enum TYPE {
        REFRESH_TOKEN,
        ACCESS_TOKEN
    }

    private TokenAuthenticationHelper() {}








    public static String addTokenInsideCookie(HttpServletResponse res, AuthData auth, TYPE type) {
        if(type == TYPE.REFRESH_TOKEN) {
           return addTokenInsideCookie(res, auth, type, JWT_REFRESH_EXPIRATION);
        } else if(type == TYPE.ACCESS_TOKEN) {
            return addTokenInsideCookie(res, auth, type, JWT_ACCESS_EXPIRATION);
        }
        return null;
    }

    public static String addTokenInsideCookie(HttpServletResponse res, AuthData auth, TYPE type, long tokenExpiration) {

        String jwt = generateToken(auth, type, tokenExpiration);

        String prefix = type == TYPE.ACCESS_TOKEN ? JWT_ACCESS_PREFIX : JWT_REFRESH_PREFIX;

        Cookie cookie = new Cookie(prefix, jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        return jwt;
    }


    public static String generateToken(AuthData auth, TYPE type, long tokenExpiration) {
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
    }

    public static String generateToken(AuthData auth, TYPE type) {
        return generateToken(auth, type, type == TYPE.ACCESS_TOKEN ? JWT_ACCESS_EXPIRATION : JWT_REFRESH_EXPIRATION);
    }


    static Authentication getAuthentication(HttpServletRequest request) {

        String token = extractToken(request, TYPE.ACCESS_TOKEN);
        token = token.substring(JWT_ACCESS_PREFIX.length());



        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();

            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get("authorities").toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            String userName = claims.getSubject();

            if(userName == null) {
                return null;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userName, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            return authentication;
        }
        return null;
    }

    public static String extractToken(HttpServletRequest request, TYPE tokenType){
        /*Cookie cookie = WebUtils.getCookie(request, tokenType == TYPE.ACCESS_TOKEN ? JWT_ACCESS_PREFIX : JWT_REFRESH_PREFIX);

        return cookie != null ? cookie.getValue() : null;*/

        String tokenHeader = tokenType == TYPE.ACCESS_TOKEN ? JWT_ACCESS_HEADER : JWT_REFRESH_HEADER;

        return request.getHeader(tokenHeader);
    }

    public static long getJwtAccessExpiration() {
        return JWT_ACCESS_EXPIRATION;
    }

    public static long getJwtRefreshExpiration() {
        return JWT_REFRESH_EXPIRATION;
    }
}