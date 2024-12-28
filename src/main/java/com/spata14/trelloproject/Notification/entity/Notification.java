package com.spata14.trelloproject.Notification.entity;

import com.spata14.trelloproject.Notification.entity.enums.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @Column(nullable = false, length = 50)
    private String id;

    @Column(nullable = false)
    private Long workSpaceId;

    @Column(nullable = false)
    private String message;

    @Column( nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    private Boolean isRead = false;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Notification(EventType type, Long eventId, Long workSpaceId, String message, Boolean isRead) {
        if (type == null || eventId == null) {
            throw new IllegalArgumentException("type and eventId cannot be null");
        }
        this.id = String.format("%s-%d", type.getType(), eventId); // id 생성 로직
        this.workSpaceId = workSpaceId;
        this.message = message;
        this.isRead = isRead != null ? isRead : false; // null일 경우 기본값 false 설정
        this.createdAt = LocalDateTime.now();
    }
}
