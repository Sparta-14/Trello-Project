package com.spata14.trelloproject.workspace.service;

import com.spata14.trelloproject.common.util.SessionUtils;
import com.spata14.trelloproject.user.Token;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.workspace.WorkspaceMemberRole;
import com.spata14.trelloproject.user.service.UserService;
import com.spata14.trelloproject.workspace.Workspace;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import com.spata14.trelloproject.workspace.dto.InviteMemberRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceRequestDto;
import com.spata14.trelloproject.workspace.dto.WorkspaceResponseDto;
import com.spata14.trelloproject.workspace.exception.WorkspaceErrorCode;
import com.spata14.trelloproject.workspace.exception.WorkspaceException;
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

    /**
     * 워크스페이스 생성
     * @param dto {@link WorkspaceRequestDto} - name(String), description(String)
     * @return {@link WorkspaceResponseDto} - id(Long), name(String), description(String), createdAt(LocalDateTime), modifiedAt(LocalDateTime)
     */
    @Transactional
    public WorkspaceResponseDto create(WorkspaceRequestDto dto) {
        User mySelf = userService.findUserByEmail(sessionUtils.getLoginUserEmail());

        // 워크스페이스 생성 저장
        Workspace workspace = new Workspace(dto.getName(), dto.getDescription());
        workspaceRepository.save(workspace);

        //생성자 역할을 WORKSPACE 로 설정
        workspaceUserRepository.save(new WorkspaceUser(mySelf, workspace, WorkspaceMemberRole.WORKSPACE));

        return WorkspaceResponseDto.toDto(workspace);
    }

    /**
     * 멤버 추가
     * @param id 워크스페이스 ID
     * @param dto {@link InviteMemberRequestDto} - email(String), workspaceMemberRole(Enum) WORKSPACE, BOARD, READ_ONLY
     * @return String (user.getEmail() + " 님을 워크스페이스에 초대하였습니다.")
     */
    @Transactional
    public String addMember(Long id, InviteMemberRequestDto dto) {
        // 워크스페이스 역할 체크
        WorkspaceUser workspaceUser = workspaceUserRepository.getWorkspaceOwnerOrElseThrow(sessionUtils.getLoginUserEmail(), WorkspaceMemberRole.WORKSPACE, id);

        // 초대 중복 방지
        if (workspaceUserRepository.isMember(id, dto.getEmail())) {
            throw new WorkspaceException(WorkspaceErrorCode.WORKSPACE_DUPLICATED_MEMBER);
        }

        // 멤버로 추가할 유저
        User user = userService.findUserByEmail(dto.getEmail());

        // 워크 스페이스에 유저 저장, 역할 지정
        workspaceUserRepository.save(new WorkspaceUser(user, workspaceUser.getWorkspace(), dto.getWorkspaceMemberRole()));

        return user.getEmail() + " 님을 워크스페이스에 초대하였습니다.";
    }

    /**
     * 본인이 포함된 워크스페이스 모두 조회
     * @return {@link WorkspaceResponseDto} - id(Long), name(String), description(String), createdAt(LocalDateTime), modifiedAt(LocalDateTime)
     */
    public List<WorkspaceResponseDto> findAll() {
        return workspaceUserRepository.findAllWorkspaceByUser(sessionUtils.getLoginUserEmail());
    }

    /**
     * 워크스페이스 수정
     * @param id 워크스페이스 ID
     * @param dto {@link WorkspaceRequestDto} - name(String), description(String)
     * @return {@link WorkspaceResponseDto} - id(Long), name(String), description(String), createdAt(LocalDateTime), modifiedAt(LocalDateTime)
     */
    @Transactional
    public WorkspaceResponseDto update(Long id, WorkspaceRequestDto dto) {
        WorkspaceUser workspaceUser = workspaceUserRepository.getWorkspaceOwnerOrElseThrow(sessionUtils.getLoginUserEmail(), WorkspaceMemberRole.WORKSPACE, id);
        Workspace workspace = workspaceUser.getWorkspace();
        workspace.updateWorkspace(dto.getName(), dto.getDescription());
        workspaceRepository.save(workspace);

        return WorkspaceResponseDto.toDto(workspace);
    }

    /**
     * 워크스페이스 삭제
     * @param id 워크스페이스 ID
     */
    @Transactional
    public void delete(Long id) {
        WorkspaceUser workspaceUser = workspaceUserRepository.getWorkspaceOwnerOrElseThrow(sessionUtils.getLoginUserEmail(), WorkspaceMemberRole.WORKSPACE, id);
        workspaceRepository.delete(workspaceUser.getWorkspace());
    }

    // 워크스페이스 내부 유저들의 토큰 조회
    public List<Token> getAllUserTokens(Long workspaceId) {
        return workspaceUserRepository.indTokensByWorkspaceIdOrElseThrow(workspaceId);
    }
}
