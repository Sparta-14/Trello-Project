package com.spata14.trelloproject.card.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CardErrorCode {

    RESPONSE_INCORRECT(HttpStatus.BAD_REQUEST, "잘못된 입력값입니다."),
    ONLY_WRITER_UPDATE(HttpStatus.UNAUTHORIZED, "작성자만 수정할 수 있습니다."),
    CARD_NOT_FOUNT(HttpStatus.NOT_FOUND,"해당 id의 카드가 없습니다."),
    IN_CHARGE_USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 사용자가 없습니다."),
    FILE_NOT_FOUNT(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다. ");

    private final HttpStatus httpStatus;
    private final String message;
}
