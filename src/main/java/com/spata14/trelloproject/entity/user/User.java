package com.spata14.trelloproject.entity.user;

import com.spata14.trelloproject.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 15)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 72)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status; // Active, NonActive (회원 탈퇴 관리)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role; //USER, ADMIN

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserAuthorization auth; // NONE, READ, ALL

    protected User() {}

    public User(String name, String email, String password, UserRole role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = UserStatus.ACTIVATE;
        this.role = role;
        this.auth = UserAuthorization.NONE;
    }

    //TODO : 연관 관계

}
