package com.spata14.trelloproject.user.repository;

import com.spata14.trelloproject.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
         new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 이메일입니다."));
    }
    Optional<User> findByEmail(String email);
}
