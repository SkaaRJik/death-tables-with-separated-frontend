package ru.filippov.neatvue.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenAuthenticationHelper {





    private static String JWT_SECRET;


    private static long JWT_ACCESS_EXPIRATION;


    private static long JWT_REFRESH_EXPIRATION;


    private static String JWT_ACCESS_PREFIX;

    private static String JWT_REFRESH_PREFIX;


    private static String JWT_HEADER;

    @Value("${app.jwt.Secret}")
    public  void setJwtSecret(String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }
    @Value("${app.jwt.Access_Expiration}")
    public  void setJwtAccessExpiration(long jwtAccessExpiration) {
        JWT_ACCESS_EXPIRATION = jwtAccessExpiration;
    }
    @Value("${app.jwt.Refresh_Expiration}")
    public  void setJwtRefreshExpiration(long jwtRefreshExpiration) {
        JWT_REFRESH_EXPIRATION = jwtRefreshExpiration;
    }
    @Value("${app.jwt.prefix.access}")
    public  void setJwtPrefix(String jwtPrefix) {
        JWT_ACCESS_PREFIX = jwtPrefix;
    }
    @Value("${app.jwt.Header}")
    public  void setJwtHeader(String jwtHeader) {
        JWT_HEADER = jwtHeader;
    }

    @Value("${app.jwt.prefix.refresh}")

    public void setJwtRefreshPrefix(String jwtRefreshPrefix) {
        JWT_REFRESH_PREFIX = jwtRefreshPrefix;
    }

    enum TYPE {
        REFRESH_TOKEN,
        ACCESS_TOKEN
    }
    private TokenAuthenticationHelper() {    }








    static String addAuthentication(HttpServletResponse res, Authentication auth, TYPE type) {
        if(type == TYPE.REFRESH_TOKEN) {
           return addAuthentication(res, auth, type, JWT_REFRESH_EXPIRATION);
        } else if(type == TYPE.ACCESS_TOKEN) {
            return addAuthentication(res, auth, type, JWT_ACCESS_EXPIRATION);
        }
        return null;
    }

    static String addAuthentication(HttpServletResponse res, Authentication auth, TYPE type, long tokenExpiration) {

        String jwt = getnerateToken(auth, type, tokenExpiration);

        String prefix = type == TYPE.ACCESS_TOKEN ? JWT_ACCESS_PREFIX : JWT_REFRESH_PREFIX;

        Cookie cookie = new Cookie(prefix, jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        res.addCookie(cookie);
        return jwt;
    }

    public static String getnerateToken(Authentication auth, TYPE type, long tokenExpiration) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(auth.getName());


        if(type== TYPE.ACCESS_TOKEN) {
            jwtBuilder.claim("authorities", authorities);

        } else if(type== TYPE.REFRESH_TOKEN){
            jwtBuilder.claim("authorities", authorities);
        }

        jwtBuilder.setExpiration(new Date()).setExpiration(new Date(System.currentTimeMillis() + tokenExpiration));


        return jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    private static String getnerateToken(Authentication auth, TYPE type) {
        return getnerateToken(auth, type, type == TYPE.ACCESS_TOKEN ? JWT_ACCESS_EXPIRATION : JWT_REFRESH_EXPIRATION);
    }


    static Authentication getAuthentication(HttpServletRequest request) {





        String token = extractToken(request, TYPE.ACCESS_TOKEN);



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
            return userName != null ? new UsernamePasswordAuthenticationToken(userName, null, authorities) : null;
        }
        return null;
    }

    static String extractToken(HttpServletRequest request, TYPE tokenType){
        Cookie cookie = WebUtils.getCookie(request, tokenType == TYPE.ACCESS_TOKEN ? JWT_ACCESS_PREFIX : JWT_REFRESH_PREFIX);

        return cookie != null ? cookie.getValue() : null;
    }

}