package com.spata14.trelloproject.user.repository;

import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.exception.UserErrorCode;
import com.spata14.trelloproject.user.exception.UserException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 이메일로 유저를 찾고 없으면 예외처리
     * @param email - 유저 이메일
     * @return {@link User}
     */
    default User findByEmailOrElseThrow(String email) {
        return findByEmail(email).orElseThrow(() ->
                new UserException(UserErrorCode.EMAIL_NOT_FOUND));
    }

    Optional<User> findByEmail(String email);

    /**
     * 아이디로 유저를 찾고 없으면 예외처리
     * @param id - 유저 ID
     * @return {@link User}
     */
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new UserException(UserErrorCode.USER_NOT_FOUND));
    }
}
