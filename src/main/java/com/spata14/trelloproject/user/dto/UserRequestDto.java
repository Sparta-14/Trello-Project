package com.spata14.trelloproject.user.dto;

import com.spata14.trelloproject.user.UserAuth;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRequestDto {
    private final String name;
    private final String email;
    private final String password;
    private final UserAuth auth;
}
