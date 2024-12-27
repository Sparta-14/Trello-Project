package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.user.UserRole;
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
            " and wu.userRole = :userRole" +
            " and wu.workspace.id = :workspaceId")
    Optional<WorkspaceUser> getWorkspaceOwner(@Param("email") String email, @Param("userRole")UserRole userRole, @Param("workspaceId") Long workspaceId);

    /**
     *
     * @param email - 유저 이메일
     * @param userRole - 워크스페이스 내 유저 역할
     * @param workspaceId - 워크스페이스 ID
     * @return {@link WorkspaceUser}
     */
    default WorkspaceUser getWorkspaceOwnerOrElseThrow(String email, UserRole userRole, Long workspaceId) {
        return getWorkspaceOwner(email, userRole, workspaceId).orElseThrow(() -> new WorkspaceException(WorkspaceErrorCode.WORKSPACE_UNAUTHORIZED));
    }
}
