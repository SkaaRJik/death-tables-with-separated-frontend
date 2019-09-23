package ru.filippov.neatvue.config.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.filippov.neatvue.domain.User;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthData {

    protected String username;
    protected List<GrantedAuthority> authorities;

    public AuthData(User user){
        this.username = user.getUsername();
        this.authorities = user.getAuthorities();
    }

    public AuthData(Authentication authentication){
        this.username = authentication.getName();
        this.authorities = authentication.getAuthorities().stream().collect(Collectors.toList());
    }

}