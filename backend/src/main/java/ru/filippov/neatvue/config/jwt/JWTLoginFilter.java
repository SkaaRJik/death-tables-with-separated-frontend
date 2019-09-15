package ru.filippov.neatvue.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.dto.ProfileDto;
import ru.filippov.neatvue.dto.SignInDto;
import ru.filippov.neatvue.dto.TokenDto;
import ru.filippov.neatvue.service.auth.AuthService;
import ru.filippov.neatvue.service.user.UserPrinciple;

import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private   AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;


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

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) {

        AuthData authData = new AuthData(auth);

        String accessToken = TokenAuthenticationHelper.generateToken(authData, TokenAuthenticationHelper.TYPE.ACCESS_TOKEN);
        String refreshToken = TokenAuthenticationHelper.generateToken(authData, TokenAuthenticationHelper.TYPE.ACCESS_TOKEN);



        String clientIp = req.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = req.getRemoteAddr();
        }

        User user = ((UserPrinciple) auth.getPrincipal()).toUser();
        String browser = res.getHeader("browser");
        String os = res.getHeader("os");

        TokenDto tokenContainer = new TokenDto(accessToken, refreshToken,
                TokenAuthenticationHelper.getJwtAccessExpiration(), TokenAuthenticationHelper.getJwtRefreshExpiration());

        ProfileDto userProfile = ProfileDto.build(tokenContainer, auth);


        try {
            res.getOutputStream().write(objectMapper.writeValueAsString(userProfile).getBytes(StandardCharsets.UTF_8));
           /* res.getWriter().write(objectMapper.writeValueAsString(userProfile));
            res.getWriter().flush();
            res.getWriter().close();*/

        } catch (IOException e) {
            e.printStackTrace();
        }



        authService.bindToken(user, refreshToken, clientIp, browser, os);




    }

}