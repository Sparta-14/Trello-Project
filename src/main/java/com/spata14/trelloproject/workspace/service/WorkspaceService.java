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

        //생성자 권한을 ALL 로 설정 (admin)
        workspaceUserRepository.save(new WorkspaceUser(mySelf, workspace, UserRole.ALL));

        return WorkspaceResponseDto.toDto(workspace);
    }

    @Transactional
    public String addMember(Long id, InviteMemberRequestDto dto) {
        User user = userService.findUserByEmail(dto.getEmail());
        Workspace workspace = workspaceRepository.findByIdOrElseThrow(id);

        if (isMember(user)) {
            throw new IllegalArgumentException("이미 초대된 멤버입니다.");
        }

        // 권한을 READ 로 설정
        workspaceUserRepository.save(new WorkspaceUser(user, workspace, UserRole.READ));

        return user.getEmail() + " 님을 워크스페이스에 초대하였습니다.";
    }

    /**
     * 워크스페이스의 멤버인지 체크
     */
    private boolean isMember(User user) {
        return workspaceUserRepository.existsByUserId(user.getId());
    }
}
