package com.spata14.trelloproject.user.controller;

import com.spata14.trelloproject.user.dto.TokenRequestDto;
import com.spata14.trelloproject.user.dto.UserRequestDto;
import com.spata14.trelloproject.user.dto.UserResponseDto;
import com.spata14.trelloproject.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody UserRequestDto dto) {
        userService.createUser(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody UserRequestDto dto, HttpServletRequest request) {
        userService.login(dto, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 회원탈퇴
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivate(@PathVariable Long id, @RequestBody UserRequestDto dto) {
        return new ResponseEntity<>(userService.patchUserStatus(id, dto), HttpStatus.OK);
    }

    /**
     * 프로필 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> viewUser(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
    }

    /**
     * 토큰 등록
     */
    @PostMapping("/{id}/token")
    public ResponseEntity<String>  registerToken(
            @PathVariable Long id,
            @RequestBody TokenRequestDto dto) {
        userService.registerToken(id, dto);
        return ResponseEntity.ok().body("토큰이 정상적으로 등록 되었습니다.");
    }

}
