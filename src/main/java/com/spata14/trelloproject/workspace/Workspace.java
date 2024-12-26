package com.spata14.trelloproject.workspace;

import com.spata14.trelloproject.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Workspace extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "workspace")
    private List<WorkspaceUser> workspaceUsers = new ArrayList<>();

    public Workspace(String name, String description) {
        this.name = name;
        this.description = description;
    }

    protected Workspace() {}
}
