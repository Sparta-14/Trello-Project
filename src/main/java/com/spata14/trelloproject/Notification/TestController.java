package com.spata14.trelloproject.Notification;

import com.spata14.trelloproject.Notification.dto.TestRequestDto;
import com.spata14.trelloproject.Notification.facade.NotificationServiceFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


// 메세지 생성 및 알람 전성 호출 테스틑 위한 임시 Controller
@Controller
@RequiredArgsConstructor
public class TestController {

    private final NotificationServiceFacade notificationServiceFacade;

    @PostMapping("/notifications/test")
    public ResponseEntity<String> test(@RequestBody TestRequestDto dto) {
        notificationServiceFacade.sendSlackMessageForProcessingOutcome(
                dto.getUserId(),
                dto.getWorkspaceId(),
                dto.getCardId(),
                dto.getCommentId()
                );
        return ResponseEntity.ok().body("정상 호출");
    }
}
