package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.user.Token;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long>, WorkspaceUserCustomRepository {

    @Query("SELECT t " +
            "FROM WorkspaceUser wu " +
            "JOIN Token t ON wu.user.id = t.user.id " +
            "WHERE wu.workspace.id = :workspaceId")
    List<Token> findTokensByWorkspaceId(@Param("workspaceId") Long workspaceId);
}
