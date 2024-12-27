package com.spata14.trelloproject.list.dto;

import com.spata14.trelloproject.list.entity.Lists;
import lombok.Getter;

@Getter
public class ListsResponseDto {
    private Long id;
    private String title;
    private Long order;

    public ListsResponseDto(Lists list) {
        this.id = list.getId();
        this.title = list.getTitle();
        this.order = list.getOrder();
    }
}
