package com.spata14.trelloproject.workspace;

import lombok.Getter;

@Getter
public enum WorkspaceMemberRole {
    READ_ONLY("readOnly"),
    BOARD("board"),
    WORKSPACE("workspace");

    private final String name;

    WorkspaceMemberRole(String name) {
        this.name = name;
    }
}
