package com.spata14.trelloproject.workspace.controller;

import com.spata14.trelloproject.workspace.dto.WorkspaceRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;
import com.spata14.trelloproject.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspaces")
@RequiredArgsConstructor
public class AdminWorkspaceController {
    private final WorkspaceService workspaceService;

    /**
     * 워크스페이스 생성
     */
    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceRequestDto dto) {
        return new ResponseEntity<>(workspaceService.create(dto), HttpStatus.CREATED);
    }
}
