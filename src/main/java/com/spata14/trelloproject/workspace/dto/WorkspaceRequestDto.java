package com.spata14.trelloproject.workspace.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkspaceRequestDto {
    private final String name;
    private final String description;
}
