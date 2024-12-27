package com.spata14.trelloproject.board.exception;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardException extends RuntimeException {
    private final BoardErrorCode boardErrorCode;
    @Override
    public String getMessage() {
        return boardErrorCode.getMessage();
    }
}
