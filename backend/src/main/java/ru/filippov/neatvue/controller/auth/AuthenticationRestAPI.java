package ru.filippov.neatvue.controller.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.filippov.neatvue.service.auth.AuthService;
import ru.filippov.neatvue.service.user.UserDetailsServiceImpl;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/secured/user/auth")
@Slf4j
public class AuthenticationRestAPI {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    AuthService authService;



    @GetMapping("/refresh-token")
    public ResponseEntity refreshTokens(@RequestParam("token") final String oldRefreshToken, HttpServletResponse response) {

        try {
            authService.refreshTokens(oldRefreshToken, response);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }  catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }


        return ResponseEntity.ok("Tokens were refreshed");
    }
}