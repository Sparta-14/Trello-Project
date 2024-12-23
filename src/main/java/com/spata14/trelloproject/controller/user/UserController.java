package com.spata14.trelloproject.controller.user;

import com.spata14.trelloproject.dto.user.UserRequestDto;
import com.spata14.trelloproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserRequestDto dto) {
        userService.signUp(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
