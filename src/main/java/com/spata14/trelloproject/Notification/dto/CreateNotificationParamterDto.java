package com.spata14.trelloproject.Notification.dto;

import com.spata14.trelloproject.Notification.entity.enums.EventType;
import com.spata14.trelloproject.Notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateNotificationParamterDto {

    private EventType type;
    private Long eventId;
    private Long workSpaceId;
    private String message;

    @Builder
    public CreateNotificationParamterDto(EventType type, Long eventId, Long workSpaceId, String message) {
        this.type = type;
        this.eventId = eventId;
        this.workSpaceId = (workSpaceId == null)? eventId : workSpaceId;
        this.message = message;
    }

    public Notification toEntity() {
        return Notification.builder()
                .type(this.type)
                .eventId(this.eventId)
                .workSpaceId(this.workSpaceId)
                .message(this.message)
                .build();
    }
}
