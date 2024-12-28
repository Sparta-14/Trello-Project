package com.spata14.trelloproject.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private Long cardId;
    private String text;
}
