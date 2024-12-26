package com.spata14.trelloproject.workspace.controller;

import com.spata14.trelloproject.workspace.dto.InviteMemberRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;
import com.spata14.trelloproject.workspace.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/workspaces")
@RequiredArgsConstructor
public class AdminWorkspaceController {
    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<WorkspaceResponseDto> createWorkspace(@RequestBody WorkspaceRequestDto dto) {
        return new ResponseEntity<>(workspaceService.create(dto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> addMember(@PathVariable Long id, @RequestBody InviteMemberRequestDto dto) {
        String result = workspaceService.addMember(id, dto);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
