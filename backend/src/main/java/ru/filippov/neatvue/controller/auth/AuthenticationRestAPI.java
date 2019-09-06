package ru.filippov.neatvue.controller.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.filippov.neatvue.config.jwt.TokenAuthenticationHelper;
import ru.filippov.neatvue.config.jwt.TokenProvider;
import ru.filippov.neatvue.dto.TokenDto;
import ru.filippov.neatvue.service.auth.AuthService;
import ru.filippov.neatvue.service.user.UserDetailsServiceImpl;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/secured/user/auth")
@Slf4j
public class AuthenticationRestAPI {

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    AuthService authService;

    @Autowired
    TokenProvider jwtProvider;

    @PutMapping("/refresh-token")
    public ResponseEntity<TokenDto> refreshTokens(final HttpServletRequest request, HttpServletResponse response) {
        TokenDto newTokens = null;



        


        try {
            newTokens = authService.refreshTokens(tokens.getRefreshToken(), this.jwtProvider);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new ResponseEntity<TokenDto>( new TokenDto("", "", 0, 0),
                    HttpStatus.UNAUTHORIZED);
        }


        return ResponseEntity.ok().body(newTokens);
    }
}