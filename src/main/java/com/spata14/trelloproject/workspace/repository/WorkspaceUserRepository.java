package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.user.UserRole;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import com.spata14.trelloproject.workspace.exception.WorkspaceErrorCode;
import com.spata14.trelloproject.workspace.exception.WorkspaceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    default WorkspaceUser getWorkspaceOwnerOrElseThrow(String email, UserRole userRole, Long workspaceId) {
        return getWorkspaceOwner(email, userRole, workspaceId).orElseThrow(() -> new WorkspaceException(WorkspaceErrorCode.WORKSPACE_UNAUTHORIZED));
    }
}
