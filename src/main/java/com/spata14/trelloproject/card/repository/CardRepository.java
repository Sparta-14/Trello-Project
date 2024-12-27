package com.spata14.trelloproject.card.repository;

import com.spata14.trelloproject.card.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
}
