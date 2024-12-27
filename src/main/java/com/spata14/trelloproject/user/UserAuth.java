package com.spata14.trelloproject.user;

import lombok.Getter;

@Getter
public enum UserAuth {
    ADMIN("admin"),
    USER("user");

    private final String name;

    UserAuth(String name) {
        this.name = name;
    }
}
