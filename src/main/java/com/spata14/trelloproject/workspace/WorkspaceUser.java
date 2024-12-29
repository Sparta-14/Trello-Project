package com.spata14.trelloproject.workspace;

import com.spata14.trelloproject.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class WorkspaceUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkspaceMemberRole workspaceMemberRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    public WorkspaceUser(User user, Workspace workspace, WorkspaceMemberRole workspaceMemberRole) {
        this.user = user;
        this.workspace = workspace;
        this.workspaceMemberRole = workspaceMemberRole;
    }

    protected WorkspaceUser() {}
}
