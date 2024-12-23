package com.spata14.trelloproject.dto.user;

import com.spata14.trelloproject.entity.user.User;
import com.spata14.trelloproject.entity.user.UserAuthorization;
import com.spata14.trelloproject.entity.user.UserRole;
import com.spata14.trelloproject.entity.user.UserStatus;
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
    private final UserRole role;
    private final UserAuthorization auth;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                user.getRole(),
                user.getAuth(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }
}
