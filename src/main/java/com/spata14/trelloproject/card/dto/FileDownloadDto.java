package com.spata14.trelloproject.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileDownloadDto {
    private byte [] bytes;
    private String type;

}
