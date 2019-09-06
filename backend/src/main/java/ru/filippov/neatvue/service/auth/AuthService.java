package ru.filippov.neatvue.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filippov.neatvue.config.jwt.TokenProvider;
import ru.filippov.neatvue.domain.Auth;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.dto.TokenDto;
import ru.filippov.neatvue.repository.AuthRepository;

import javax.security.sasl.AuthenticationException;

@Slf4j
@Service
public class AuthService {

    @Autowired
    AuthRepository authRepository;

    @Transactional
    public void bindToken(User user, String token, String clientIp, String browser, String os){
        Auth auth = authRepository.findByUserAndIpAndOs(user, clientIp, os).orElse(null);
        if(auth == null) {
            addRefreshToken(user, token, clientIp, browser, os);
        } else {
            updateToken(auth, token);
        }
    }

    @Transactional
    public void addRefreshToken(User user, String token, String clientIp, String browser, String os){
        Auth auth = Auth.builder()
                .user(user)
                .refreshToken(token)
                .ip(clientIp)
                .browser(browser)
                .os(os)
                .build();
        authRepository.save(auth);
    }

    @Transactional
    public void updateToken(Auth auth, String token){
        auth.setRefreshToken(token);
        authRepository.save(auth);
    }

    @Transactional
    public TokenDto refreshTokens (String refreshToken, TokenProvider tokenProvider) throws AuthenticationException {
        TokenDto tokens = null;

        Auth auth = authRepository.findByRefreshToken(refreshToken).orElseThrow(AuthenticationException::new);

        tokens = new TokenDto(
                tokenProvider.generateAccessToken(auth.getUser()),
                tokenProvider.generateRefreshToken(auth.getUser()),
                tokenProvider.getAccessTokenExpiration(),
                tokenProvider.getRefreshTokenExpiration()
        );

        auth.setRefreshToken(tokens.getRefreshToken());

        authRepository.save(auth);


        return tokens;



    }



}
