package com.spata14.trelloproject.Notification.dto;

import lombok.Getter;

@Getter
public class TestRequestDto {

    private final Long userId;
    private final Long workspaceId;
    private final Long cardId;
    private final Long commentId;

    public TestRequestDto(Long userId, Long workspaceId, Long cardId, Long commentId) {
        this.userId = userId;
        this.workspaceId = workspaceId;
        this.cardId = cardId;
        this.commentId = commentId;
    }
}
