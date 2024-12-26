package com.spata14.trelloproject.user.service;

import com.spata14.trelloproject.common.SessionNames;
import com.spata14.trelloproject.common.util.PasswordEncoder;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.dto.UserRequestDto;
import com.spata14.trelloproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserRequestDto dto) {
        boolean duplicated = userRepository.findByEmail(dto.getEmail()).isPresent();
        if (duplicated) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 이메일입니다.");
        }

        userRepository.save(new User(dto.getName(), dto.getEmail(), PasswordEncoder.encode(dto.getPassword()), dto.getAuth()));
    }

    public void login(UserRequestDto dto, HttpServletRequest request) {
        User findUser = userRepository.findByEmailOrElseThrow(dto.getEmail());
        if (!PasswordEncoder.matches(dto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        HttpSession session = request.getSession(true);
        session.setAttribute(SessionNames.USER_AUTH, dto.getEmail());
    }
}
