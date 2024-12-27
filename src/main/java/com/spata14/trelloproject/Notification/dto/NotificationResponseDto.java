package com.spata14.trelloproject.Notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificationResponseDto {

    private String id;
    private String message;
    private LocalDateTime createdAt;


    @Builder
    public NotificationResponseDto(String id, String message, LocalDateTime createdAt) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
    }
}
