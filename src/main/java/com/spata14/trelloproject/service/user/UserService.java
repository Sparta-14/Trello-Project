package com.spata14.trelloproject.service.user;

import com.spata14.trelloproject.dto.user.UserRequestDto;
import com.spata14.trelloproject.dto.user.UserResponseDto;
import com.spata14.trelloproject.entity.user.User;
import com.spata14.trelloproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void signUp(UserRequestDto dto) {
        User user = new User(dto.getName(), dto.getEmail(), dto.getPassword(), dto.getRole());
        userRepository.save(user);
    }
}
