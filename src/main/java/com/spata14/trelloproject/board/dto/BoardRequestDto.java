package com.spata14.trelloproject.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
@Getter
@AllArgsConstructor
public class BoardRequestDto {

    @NotBlank(message = "제목은 비어있을 수 없습니다.")
    private String title;

    private String color;

    private String imageUrl;
}
