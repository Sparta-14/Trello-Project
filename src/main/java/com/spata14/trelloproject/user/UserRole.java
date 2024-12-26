package com.spata14.trelloproject.user;

import lombok.Getter;

@Getter
public enum UserRole {
    NONE("none"),
    READ("read"),
    ALL("all");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }
}
