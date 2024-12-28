package com.spata14.trelloproject.Notification.utiil;

import com.spata14.trelloproject.Notification.entity.enums.EventType;
import org.springframework.stereotype.Component;

@Component
public class  EventMapperUtil {

    public EventResult identifyEventTypeAndCreateMessage(Long workspaceId, Long cardId, Long commentId) {
        Long eventId;
        EventType eventType;
        String message;

        if (commentId != null) {
            eventId = commentId;
            eventType = EventType.COMMENT;
            message = buildMessage(workspaceId, cardId, commentId, eventType);
        } else if (cardId != null) {
            eventId = cardId;
            eventType = EventType.CARD;
            message = buildMessage(workspaceId, cardId, null, eventType);
        } else {
            eventId = workspaceId;
            eventType = EventType.WORKSPACE;
            message = buildMessage(workspaceId, null, null, eventType);
        }

        return new EventResult(eventId, eventType, message);
    }

    private String buildMessage(Long workspaceId, Long cardId, Long commentId, EventType eventType) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("워크스페이스 ").append(workspaceId);

        switch (eventType) {
            case COMMENT:
                messageBuilder.append("의 카드 ")
                        .append(cardId)
                        .append("에 댓글 ")
                        .append(commentId)
                        .append("이 추가되었습니다.");
                break;
            case CARD:
                messageBuilder.append("에 카드 ")
                        .append(cardId)
                        .append("이 생성되었습니다.");
                break;
            case WORKSPACE:
                messageBuilder.append("에 유저가 추가되었습니다.");
                break;
            default:
                throw new IllegalArgumentException("Unknown EventType: " + eventType);
        }

        return messageBuilder.toString();
    }
}
