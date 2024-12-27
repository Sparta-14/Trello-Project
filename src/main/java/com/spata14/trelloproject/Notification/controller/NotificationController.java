package com.spata14.trelloproject.Notification.controller;

import com.spata14.trelloproject.Notification.dto.NotificationResponseDto;
import com.spata14.trelloproject.Notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {

    private  final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getUnreadNotifications() {
        return ResponseEntity
                .ok()
                .body(notificationService.getUnreadNotifications());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateIsReadState(
            @PathVariable String id,
            @RequestBody Boolean isRead) {
        notificationService.updateIsReadState(id, isRead);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable String id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.ok().build();
    }
}
