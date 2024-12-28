package com.spata14.trelloproject.Notification.service;

import com.spata14.trelloproject.Notification.dto.CreateNotificationParamterDto;
import com.spata14.trelloproject.Notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(CreateNotificationParamterDto parameterDto) {
        notificationRepository.save(parameterDto.toEntity());
    }
}
