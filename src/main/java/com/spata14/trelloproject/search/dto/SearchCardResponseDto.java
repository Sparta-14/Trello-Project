package com.spata14.trelloproject.search.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCardResponseDto {

    private Long id;
    private Long userId;
    private String title;

    @Builder
    public SearchCardResponseDto(Long id, Long userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }
}
