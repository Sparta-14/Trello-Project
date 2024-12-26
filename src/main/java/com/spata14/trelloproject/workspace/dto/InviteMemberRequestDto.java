package com.spata14.trelloproject.workspace.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InviteMemberRequestDto {
    private final String email;
}
