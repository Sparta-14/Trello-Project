package com.spata14.trelloproject.card.dto;

import com.spata14.trelloproject.entity.BaseEntity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

//@Entity
@RequiredArgsConstructor
public class Card extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String content;
    private LocalDateTime endAt;




}
