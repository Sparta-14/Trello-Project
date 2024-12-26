package com.spata14.trelloproject.workspace.repository;

import com.spata14.trelloproject.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    default Workspace findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
