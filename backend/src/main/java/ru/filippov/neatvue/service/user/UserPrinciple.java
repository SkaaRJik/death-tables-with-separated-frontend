package ru.filippov.neatvue.service.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.filippov.neatvue.domain.BaseEntity;
import ru.filippov.neatvue.domain.Role;
import ru.filippov.neatvue.domain.User;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder(toBuilder = true)
public class UserPrinciple implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private String avatar;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private LocalDateTime creationDate;

    private LocalDateTime lastPasswordUpdate;

    public static UserPrinciple toUserPrinciple(User user) {


        return UserPrinciple.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .firstname(user.getFirstName())
                .lastname(user.getLastName())
                .avatar(user.getAvatar())
                .authorities(user.getAuthorities())
                .creationDate(user.getCreationDate())
                .lastPasswordUpdate(user.getLastPasswordUpdate())
                .build();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

    public User toUser(){

        Set<Role> roles = this.authorities
                .stream().map(authority -> Role.valueOf(authority.getAuthority()))
                .collect(Collectors.toSet());




        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .password(this.password)
                .firstName(this.firstname)
                .lastName(this.lastname)
                .avatar(this.avatar)
                .roles(roles)
                .creationDate(this.creationDate)
                .lastPasswordUpdate(this.lastPasswordUpdate)
                .build();
    }


}