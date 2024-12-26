package com.spata14.trelloproject.workspace.dto;

import com.spata14.trelloproject.workspace.Workspace;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class WorkspaceResponseDto {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public static WorkspaceResponseDto toDto(Workspace workspace) {
        return new WorkspaceResponseDto(workspace.getId(), workspace.getName(), workspace.getDescription(), workspace.getCreatedAt(), workspace.getModifiedAt());
    }
}
