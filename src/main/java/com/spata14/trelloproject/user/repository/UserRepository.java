package com.spata14.trelloproject.user.repository;

import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.exception.UserErrorCode;
import com.spata14.trelloproject.user.exception.UserException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new UserException(UserErrorCode.EMAIL_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);

    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
