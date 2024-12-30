package com.spata14.trelloproject.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadDto {
    private Long id;
    private String name;
    private byte [] bytes;
    private String type;

}
