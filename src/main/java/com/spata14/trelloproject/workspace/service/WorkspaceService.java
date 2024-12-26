package com.spata14.trelloproject.workspace.service;

import com.spata14.trelloproject.workspace.Workspace;
import com.spata14.trelloproject.workspace.dto.WorkspaceRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;
import com.spata14.trelloproject.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public WorkspaceResponseDto create(WorkspaceRequestDto dto) {
        Workspace workspace = new Workspace(dto.getName(), dto.getDescription());
        workspaceRepository.save(workspace);
        return WorkspaceResponseDto.toDto(workspace);
    }
}
