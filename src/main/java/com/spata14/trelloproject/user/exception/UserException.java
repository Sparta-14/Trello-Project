package com.spata14.trelloproject.user.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserException extends RuntimeException {
    private final UserErrorCode userErrorCode;

    @Override
    public String getMessage() {
        return userErrorCode.getMessage();
    }
}
