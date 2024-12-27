package com.spata14.trelloproject.card.dto;

import com.spata14.trelloproject.card.entity.FileData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class CardResponseDto {
    private Long id;
    private String title;
    private String content;
    //    private LocalDateTime endAt;
    private List<FileData> fileDataList;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CardResponseDto(Long id, String title, String content, List<FileData> fileDataList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.fileDataList = fileDataList;
        this.createAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    public CardResponseDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
