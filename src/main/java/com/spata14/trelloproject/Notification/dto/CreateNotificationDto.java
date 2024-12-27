package com.spata14.trelloproject.Notification.dto;

import com.spata14.trelloproject.Notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNotificationDto {

    private EventType type;
    private Long eventId;
    private Long workSpaceId;
    private String title;
    private String content;

    public Notification toEntity() {
        return Notification.builder()
                .type(this.type)
                .eventId(this.eventId)
                .workSpaceId(this.workSpaceId)
                .title(this.title)
                .content(this.content)
                .build();
    }
}
