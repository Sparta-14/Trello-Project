package com.spata14.trelloproject.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class BoardRequestDto {
    private String title;
    private String color;
    private String imageUrl;
}
