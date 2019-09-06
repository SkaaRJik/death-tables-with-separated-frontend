package ru.filippov.neatvue.service.user;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.filippov.neatvue.domain.Role;
import ru.filippov.neatvue.domain.User;
import ru.filippov.neatvue.dto.SignUpDto;
import ru.filippov.neatvue.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Value("${app.default.avatar}")
    private String DEFAULT_AVATAR;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User Not Found with username : " + username)
                );

        return UserPrinciple.toUserPrinciple(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByEmail(username);
    }

    public void registrate(SignUpDto signUpRequest) throws PSQLException {

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .firstName(signUpRequest.getFirstName())
                .lastName(signUpRequest.getLastName())
                .active(true)
                .password(encoder.encode(signUpRequest.getPassword()))
                .roles( new HashSet<Role>(1) {{add(Role.USER);}})
                .avatar( this.DEFAULT_AVATAR)
                //.creationDate(LocalDateTime.now())
                .lastPasswordUpdate(LocalDateTime.now())
                .build();

        userRepository.save(user);

    }
}