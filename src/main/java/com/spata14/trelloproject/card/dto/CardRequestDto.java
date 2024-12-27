package com.spata14.trelloproject.card.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardRequestDto {
    private String title;
    private String content;
    private LocalDateTime endAt;
    private List<Long> inChargeUsers;

}
