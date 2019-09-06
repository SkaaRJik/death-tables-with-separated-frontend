package ru.filippov.neatvue.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.input.CloseShieldInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.dto.SignInDto;
import ru.filippov.neatvue.service.auth.AuthService;
import ru.filippov.neatvue.service.user.UserDetailsServiceImpl;
import ru.filippov.neatvue.service.user.UserPrinciple;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthService authService;


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

        TokenAuthenticationHelper.addAuthentication(res, auth, TokenAuthenticationHelper.TYPE.ACCESS_TOKEN);
        String refreshToken = TokenAuthenticationHelper.addAuthentication(res, auth, TokenAuthenticationHelper.TYPE.REFRESH_TOKEN);

        String clientIp = req.getHeader("X-FORWARDED-FOR");
        if (clientIp == null || "".equals(clientIp)) {
            clientIp = req.getRemoteAddr();
        }

        User user = ((UserPrinciple) auth.getPrincipal()).toUser();
        String browser = res.getHeader("browser");
        String os = res.getHeader("os");
        //authService.bindToken(user, refreshToken, clientIp, browser, os);




    }


}