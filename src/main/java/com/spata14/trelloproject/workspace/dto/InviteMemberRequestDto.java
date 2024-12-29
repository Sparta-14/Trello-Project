package com.spata14.trelloproject.workspace.dto;

import com.spata14.trelloproject.workspace.WorkspaceMemberRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InviteMemberRequestDto {
    private final String email;
    private final WorkspaceMemberRole workspaceMemberRole;
}
