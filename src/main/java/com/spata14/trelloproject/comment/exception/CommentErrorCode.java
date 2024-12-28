package com.spata14.trelloproject.comment.exception;

public enum CommentErrorCode {
    CARD_NOT_FOUND("CARD_NOT_FOUND", "카드를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND("COMMENT_NOT_FOUND", "해당 댓글을 찾을 수 없습니다"),
    USER_NOT_FOUND("USER_NOT_FOUND", "해당 유저를 찾을 수 없습니다."),
    NO_PERMISSION("NO_PERMISSION", "해당 유저는 수행에 대한 권한이 없습니다.");

    private final String code;
    private final String message;

    CommentErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
