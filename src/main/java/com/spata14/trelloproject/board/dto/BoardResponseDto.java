package com.spata14.trelloproject.board.dto;

import com.spata14.trelloproject.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardResponseDto {
    public Long id;
    private Long workspace_id;
    private String title;
    private String color;
    private String imageUrl;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.workspace_id = board.getWorkspace().getId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.imageUrl = board.getImageUrl();
    }
}
