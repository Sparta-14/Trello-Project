package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;

import java.util.List;

public interface WorkspaceUserCustomRepository {
    // 워크스페이스 멤버인지 확인
    boolean isMember(Long workspaceId, String userEmail);

    // 특정 유저가 멤버로 있는 워크페이스 조회하기
    List<WorkspaceResponseDto> findAllWorkspaceByUser(String userEmail);
}
