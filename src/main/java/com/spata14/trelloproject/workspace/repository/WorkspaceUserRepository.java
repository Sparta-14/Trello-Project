package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.workspace.WorkspaceUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, Long> {
    boolean existsByUserId(Long userId);
}
