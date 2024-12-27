package com.spata14.trelloproject.Notification.service;

import com.spata14.trelloproject.Notification.dto.CreateNotificationParamterDto;
import com.spata14.trelloproject.Notification.dto.NotificationResponseDto;
import com.spata14.trelloproject.Notification.entity.Notification;
import com.spata14.trelloproject.Notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void createNotification(CreateNotificationParamterDto parameterDto) {
        notificationRepository.save(parameterDto.toEntity());
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> getUnreadNotifications() {
            return notificationRepository.findAllByIsReadFalseOrderByCreatedAtDesc()
                    .stream()
                    .map(Notification::toDto)
                    .collect(Collectors.toList());
    }

    @Transactional
    public void updateIsReadState(String id,  Boolean isRead){
        Notification findNotification = notificationRepository.findByIdOrElseThrow(id);
        findNotification.updateIsRead(isRead);
    }

    @Transactional
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }

}
