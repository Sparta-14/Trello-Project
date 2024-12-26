package com.spata14.trelloproject.user;

import com.spata14.trelloproject.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "users")
@DynamicUpdate
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
    private UserStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserAuth auth; // NONE, READ, ALL

    protected User() {}

    public User(String name, String email, String password, UserAuth auth) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = UserStatus.ACTIVATED;
        this.auth = auth;
    }

    //TODO : 연관 관계

    //편의 메서드
    public void deactivateUser() {
        this.status = UserStatus.DEACTIVATED;
    }
}
