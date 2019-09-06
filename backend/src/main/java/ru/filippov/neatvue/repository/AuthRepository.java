package ru.filippov.neatvue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.filippov.neatvue.domain.Auth;
import ru.filippov.neatvue.domain.User;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
    Optional<Auth> findById(Long id);
    Optional<Auth> findByRefreshToken(String token);
    Optional<Auth> findByIp(String ip);
    Optional<Auth> findByUserAndIpAndOs(User user,String ip, String os);


}
