package com.spata14.trelloproject.workspace.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum WorkspaceErrorCode {
    WORKSPACE_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "워크스페이스 관리자 권한이 없습니다."),
    WORKSPACE_DUPLICATED_MEMBER(HttpStatus.NOT_ACCEPTABLE, "이미 초대된 멤버입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
