package com.spata14.trelloproject.Notification.facade;

import com.spata14.trelloproject.Notification.dto.CreateNotificationParamterDto;
import com.spata14.trelloproject.Notification.exception.NotificationErrorCode;
import com.spata14.trelloproject.Notification.exception.NotificationException;
import com.spata14.trelloproject.Notification.service.SlackMessageService;
import com.spata14.trelloproject.Notification.utiil.EventResult;
import com.spata14.trelloproject.Notification.service.NotificationService;
import com.spata14.trelloproject.Notification.utiil.EventMapperUtil;
import com.spata14.trelloproject.user.Token;
import com.spata14.trelloproject.user.exception.UserErrorCode;
import com.spata14.trelloproject.workspace.service.WorkspaceService;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationServiceFacade {

    private final EventMapperUtil eventMapperUtil;
    private final WorkspaceService workspaceService;
    private final NotificationService notificationService;
    private final SlackMessageService slackMessageService;

    /** 이벤트 트리거 이후 발생되는 메세지 생성 및 slack 알림 연동
     *
     *<P> * 이벤트 트리커 *</p>
     * <P>워크스페이스 멤버 추가</p>
     * <P>카드 생성</p>
     * <P>댓글 생성</p>
     *
     * @param workspaceId
     * @param cardId
     * @param commentId
     */

    public void sendSlackMessageForProcessingOutcome(Long workspaceId, @Nullable Long cardId, @Nullable  Long commentId) {
        EventResult eventResult = eventMapperUtil.identifyEventTypeAndCreateMessage(workspaceId, cardId, commentId);

        // 메세지 생성
        CreateNotificationParamterDto dto = CreateNotificationParamterDto.builder()
                .type(eventResult.getEventType())
                .eventId(eventResult.getEventId())
                .message(eventResult.getMessage())
                .build();

        // 메세지 저장
        notificationService.createNotification(dto);

        // 워크스페이스에 포함된 유저들에게 메세지 전송
        List<Token> allUserTokens = workspaceService.getAllUserTokens(workspaceId);

        if(isTokenValid(allUserTokens)) {
            throw new NotificationException(NotificationErrorCode.TOKEN_MISSING);
        }

        allUserTokens.forEach(token -> slackMessageService.sendMessage(token.getToken(), eventResult.getMessage()));
    }

    private boolean isTokenValid(List<Token> allUserTokens) {
        return allUserTokens.isEmpty();
    }
}


