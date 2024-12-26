package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
