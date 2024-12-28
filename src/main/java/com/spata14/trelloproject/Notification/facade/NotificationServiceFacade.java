package com.spata14.trelloproject.Notification.facade;

import com.spata14.trelloproject.Notification.dto.CreateNotificationParamterDto;
import com.spata14.trelloproject.Notification.service.SlackMessageService;
import com.spata14.trelloproject.Notification.utiil.EventResult;
import com.spata14.trelloproject.Notification.service.NotificationService;
import com.spata14.trelloproject.Notification.utiil.EventMapperUtil;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationServiceFacade {

    private final EventMapperUtil eventMapperUtil;
    private final NotificationService notificationService;
    private final SlackMessageService slackMessageService;

    // 워크스페이스 멤버 추가 이벤트 처리
    public void sendSlackMessageForProcessingOutcome(Long userId, Long workspaceId, @Nullable Long cardId, @Nullable  Long commentId) {
        EventResult eventResult = eventMapperUtil.identifyEventTypeAndCreateMessage(workspaceId, cardId, commentId);

        // 메세지 생성
        CreateNotificationParamterDto dto = CreateNotificationParamterDto.builder()
                .type(eventResult.getEventType())
                .eventId(eventResult.getEventId())
                .message(eventResult.getMessage())
                .build();

        notificationService.createNotification(dto);


        // FIXME: 해당 워크스페이스 안의 모든 유저 ID 조회해서 넘겨야 한다
        slackMessageService.sendMessage(userId, eventResult.getMessage());
    }
}


