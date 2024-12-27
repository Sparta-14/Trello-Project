package com.spata14.trelloproject.workspace.service;

import com.spata14.trelloproject.common.util.SessionUtils;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.UserRole;
import com.spata14.trelloproject.user.service.UserService;
import com.spata14.trelloproject.workspace.Workspace;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import com.spata14.trelloproject.workspace.dto.InviteMemberRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;
import com.spata14.trelloproject.workspace.repository.WorkspaceRepository;
import com.spata14.trelloproject.workspace.repository.WorkspaceUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final SessionUtils sessionUtils;
    private final UserService userService;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceUserRepository workspaceUserRepository; // 중간 테이블

    @Transactional
    public WorkspaceResponseDto create(WorkspaceRequestDto dto) {
        User mySelf = userService.findUserByEmail(sessionUtils.getLoginUserEmail());

        // 워크스페이스 생성 저장
        Workspace workspace = new Workspace(dto.getName(), dto.getDescription());
        workspaceRepository.save(workspace);

        //생성자 역할을 ALL 로 설정 (admin)
        workspaceUserRepository.save(new WorkspaceUser(mySelf, workspace, UserRole.ALL));

        return WorkspaceResponseDto.toDto(workspace);
    }

    @Transactional
    public String addMember(Long id, InviteMemberRequestDto dto) {
        // 워크스페이스 소유자 체크
        WorkspaceUser workspaceUser = workspaceUserRepository.getWorkspaceOwnerOrElseThrow(sessionUtils.getLoginUserEmail(), UserRole.ALL, id);

        // 초대 중복 방지
        if (workspaceUserRepository.isMember(id, dto.getEmail())) {
            throw new IllegalArgumentException("이미 초대된 멤버입니다.");
        }

        // 멤버로 추가할 유저
        User user = userService.findUserByEmail(dto.getEmail());

        // 역할을 READ 로 설정
        workspaceUserRepository.save(new WorkspaceUser(user, workspaceUser.getWorkspace(), UserRole.READ));

        return user.getEmail() + " 님을 워크스페이스에 초대하였습니다.";
    }

    public List<WorkspaceResponseDto> findAll() {
        //본인이 들어가있는 워크스페이스 모두 조회
        return workspaceUserRepository.findAllWorkspaceByUser(sessionUtils.getLoginUserEmail());
    }

    @Transactional
    public WorkspaceResponseDto update(Long id, WorkspaceRequestDto dto) {
        WorkspaceUser workspaceUser = workspaceUserRepository.getWorkspaceOwnerOrElseThrow(sessionUtils.getLoginUserEmail(), UserRole.ALL, id);
        Workspace workspace = workspaceUser.getWorkspace();
        workspace.updateWorkspace(dto.getName(), dto.getDescription());
        workspaceRepository.save(workspace);

        return WorkspaceResponseDto.toDto(workspace);
    }

    @Transactional
    public void delete(Long id) {
        WorkspaceUser workspaceUser = workspaceUserRepository.getWorkspaceOwnerOrElseThrow(sessionUtils.getLoginUserEmail(), UserRole.ALL, id);
        workspaceRepository.delete(workspaceUser.getWorkspace());
    }
}
