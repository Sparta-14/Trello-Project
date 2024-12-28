package com.spata14.trelloproject.Notification.utiil;

import com.spata14.trelloproject.Notification.entity.enums.EventType;
import lombok.Getter;

@Getter
public class EventResult {
    private final Long eventId;
    private final EventType eventType;
    private final String message;

    public EventResult(Long eventId, EventType eventType, String message) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.message = message;
    }
}