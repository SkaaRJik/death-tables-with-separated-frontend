package ru.filippov.neatvue.config.jwt.alexatiks;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.filippov.neatvue.config.jwt.AuthData;
import ru.filippov.neatvue.config.jwt.TokenProvider;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.dto.ProfileDto;
import ru.filippov.neatvue.dto.SignInDto;
import ru.filippov.neatvue.dto.TokenDto;
import ru.filippov.neatvue.service.auth.AuthService;
import ru.filippov.neatvue.service.user.UserPrinciple;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/*
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private   AuthService authService;



    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
        SignInDto creds = null;
        try {
            creds = new ObjectMapper().readValue(req.getInputStream(), SignInDto.class);
            res.addHeader("os", creds.getDeviceInfo().get("os"));
            res.addHeader("browser", creds.getDeviceInfo().get("browser"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        creds.getAuthorities()
                )
        );
    }

   */ /* @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {

        AuthData authData = new AuthData(auth);

        String accessToken = tokenProvider.generateAccessToken(authData);
        String refreshToken = tokenProvider.generateRefreshToken(authData);



        String clientIp = req.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = req.getRemoteAddr();
        }

        User user = ((UserPrinciple) auth.getPrincipal()).toUser();
        String browser = res.getHeader("browser");
        String os = res.getHeader("os");

        TokenDto tokenContainer = new TokenDto(accessToken, refreshToken,
                tokenProvider.getJwtAccessExpiration(), tokenProvider.getJwtRefreshExpiration());

        ProfileDto userProfile = ProfileDto.build(auth);

        Map<String, Object> responseData = new HashMap<>(2);
        responseData.put("userProfile", userProfile);
        responseData.put("tokens", tokenContainer);

        try {
            res.getOutputStream().write(new ObjectMapper().writeValueAsString(responseData).getBytes(StandardCharsets.UTF_8));


        } catch (IOException e) {
            e.printStackTrace();
        }



        authService.bindToken(user, refreshToken, clientIp, browser, os);




    }

*/

//}