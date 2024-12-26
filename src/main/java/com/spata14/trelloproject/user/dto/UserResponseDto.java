package com.spata14.trelloproject.user.dto;

import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.UserAuth;
import com.spata14.trelloproject.user.UserStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final UserStatus status;
    private final UserAuth auth;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                user.getAuth(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
