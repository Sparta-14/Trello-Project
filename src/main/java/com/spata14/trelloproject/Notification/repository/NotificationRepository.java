package com.spata14.trelloproject.Notification.repository;

import com.spata14.trelloproject.Notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    default Notification findByIdOrElseThrow(String id) {
        return findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 알림입니다. id = " + id)
        );
    }
}
