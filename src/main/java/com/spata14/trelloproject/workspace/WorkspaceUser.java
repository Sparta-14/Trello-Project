package com.spata14.trelloproject.workspace;

import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.UserRole;
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
    private UserRole userRole;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "workspace_id", nullable = false)
    private Workspace workspace;

    public WorkspaceUser(User user, Workspace workspace, UserRole userRole) {
        this.user = user;
        this.workspace = workspace;
        this.userRole = userRole;
    }

    protected WorkspaceUser() {}
}
