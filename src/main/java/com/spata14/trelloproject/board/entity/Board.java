package com.spata14.trelloproject.board.entity;

import com.spata14.trelloproject.workspace.Workspace;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.web.ErrorResponse;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String color;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    public Board(String title, String color, String imageUrl, Workspace workspace) {
        this.title = title;
        this.color = color;
        this.imageUrl = imageUrl;
        this.workspace = workspace;
    }

    public void update(String title, String color, String imageUrl) {
        this.title = title;
        this.color = color;
        this.imageUrl = imageUrl;
    }

}
