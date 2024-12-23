package com.spata14.trelloproject.dto.user;

import com.spata14.trelloproject.entity.user.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
    private final String name;
    private final String email;
    private final String password;
    private final UserRole role;
}
