package com.spata14.trelloproject.card.repository;

import com.spata14.trelloproject.card.entity.CardUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardUserRepository extends JpaRepository<CardUser, Long> {
    List<CardUser> findAllByCardId(Long id);


}
