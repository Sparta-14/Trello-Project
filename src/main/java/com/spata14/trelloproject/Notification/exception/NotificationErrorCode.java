package com.spata14.trelloproject.Notification.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode {
    // 워크스페이스 예외
    WORKSPACE_ERROR_CODE(HttpStatus.NOT_FOUND, "해당하는 워크스페이스가 존재하지 않습니다."),

    // 토큰 예외
    TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "토큰이 존재하지 않습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
