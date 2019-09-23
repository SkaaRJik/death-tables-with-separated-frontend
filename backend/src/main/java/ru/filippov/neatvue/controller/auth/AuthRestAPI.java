package ru.filippov.neatvue.controller.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.filippov.neatvue.config.jwt.AuthData;
import ru.filippov.neatvue.config.jwt.TokenProvider;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.dto.ProfileDto;
import ru.filippov.neatvue.dto.SignInDto;
import ru.filippov.neatvue.dto.SignUpDto;
import ru.filippov.neatvue.dto.TokenDto;
import ru.filippov.neatvue.service.auth.AuthService;
import ru.filippov.neatvue.service.user.UserDetailsServiceImpl;
import ru.filippov.neatvue.service.user.UserPrinciple;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/public")
@Slf4j
public class AuthRestAPI {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsServiceImpl userService;

    @Autowired
    AuthService authService;

    @Autowired
    TokenProvider tokenProvider;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(HttpServletRequest request, @Valid @RequestBody SignInDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = ((UserPrinciple) userService.loadUserByUsername(authentication.getName())).toUser();

        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = request.getRemoteAddr();
        }

        AuthData authData = new AuthData(authentication);

        String refreshToken = tokenProvider.generateRefreshToken(authData);
        authService.bindToken(user,
                refreshToken,
                clientIp,
                loginRequest.getDeviceInfo().get("browser"),
                loginRequest.getDeviceInfo().get("os")
        );

        String accessToken = tokenProvider.generateAccessToken(authData);

        TokenDto tokenContainer = new TokenDto(
                accessToken, refreshToken, tokenProvider.getJwtAccessExpiration(), tokenProvider.getJwtRefreshExpiration()
        );
        ProfileDto userProfile = ProfileDto.build(authentication);

        Map<String, Object> responseData = new HashMap<>(2);
        responseData.put("userProfile", userProfile);
        responseData.put("tokens", tokenContainer);

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> isEmailExist(@RequestParam String email){

            return new ResponseEntity<Boolean>(userService.existsByEmail(email),
                    HttpStatus.OK);

    }





    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpRequest) {
        if(userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Ошибка. Такой e-mail уже зарегистрирован в системе",
                    HttpStatus.BAD_REQUEST);
        }

        try {
            userService.registrate(signUpRequest);
        } catch (PSQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Невозможно зарегистрировать пользователя");
        }

        return ResponseEntity.ok().body("Пользователь зарегистрирован успешно. На ваш e-mail отправлено письмо с подтверждением регистрации.");
    }

    @GetMapping("/refresh-token")
    public ResponseEntity refreshTokens(@RequestParam("token") final String oldRefreshToken, HttpServletResponse response) {

        try {
            return ResponseEntity.ok(authService.refreshTokens(oldRefreshToken));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }  catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

}