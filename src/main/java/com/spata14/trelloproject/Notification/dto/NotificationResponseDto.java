package com.spata14.trelloproject.Notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class NotificationResponseDto {

    private String id;

    private String title;

    private String content;

    private LocalDateTime createdAt;


    @Builder
    public NotificationResponseDto(String id, String title, String content, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
