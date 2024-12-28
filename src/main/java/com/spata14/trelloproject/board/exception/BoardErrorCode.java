package com.spata14.trelloproject.board.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum BoardErrorCode {

    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    RESPONSE_INCORRECT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    TITLE_EMPTY(HttpStatus.BAD_REQUEST, "제목은 비어 있을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
