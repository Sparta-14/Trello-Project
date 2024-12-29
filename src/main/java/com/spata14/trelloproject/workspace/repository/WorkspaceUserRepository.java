package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.workspace.WorkspaceMemberRole;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import com.spata14.trelloproject.workspace.exception.WorkspaceErrorCode;
import com.spata14.trelloproject.workspace.exception.WorkspaceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long>, WorkspaceUserCustomRepository {
    @Query("select wu" +
            " from WorkspaceUser wu" +
            " join fetch wu.user" +
            " join fetch wu.workspace" +
            " where wu.user.email = :email" +
            " and wu.workspaceMemberRole = :workspaceMemberRole" +
            " and wu.workspace.id = :workspaceId")
    Optional<WorkspaceUser> getWorkspaceOwner(@Param("email") String email, @Param("workspaceMemberRole") WorkspaceMemberRole workspaceMemberRole, @Param("workspaceId") Long workspaceId);

    /**
     *
     * @param email - 유저 이메일
     * @param workspaceMemberRole - 워크스페이스 내 유저 역할
     * @param workspaceId - 워크스페이스 ID
     * @return {@link WorkspaceUser}
     */
    default WorkspaceUser getWorkspaceOwnerOrElseThrow(String email, WorkspaceMemberRole workspaceMemberRole, Long workspaceId) {
        return getWorkspaceOwner(email, workspaceMemberRole, workspaceId).orElseThrow(() -> new WorkspaceException(WorkspaceErrorCode.WORKSPACE_UNAUTHORIZED));
    }
}
