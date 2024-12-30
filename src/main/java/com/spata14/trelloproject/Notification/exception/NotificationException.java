package com.spata14.trelloproject.Notification.exception;

import com.spata14.trelloproject.user.exception.UserErrorCode;
import com.spata14.trelloproject.workspace.exception.WorkspaceErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NotificationException extends RuntimeException{
    private final NotificationErrorCode notificationErrorCode;

    @Override
    public String getMessage() {
        return notificationErrorCode.getMessage();
    }
}
