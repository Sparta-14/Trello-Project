package com.spata14.trelloproject.list.entity;

import com.spata14.trelloproject.board.entity.Board;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Lists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "list_order", nullable = false)
    private Long order;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Lists(String title, Long order, Board board) {
        this.title = title;
        this.order = order;
        this.board = board;
    }

    public void update(String title, Long order) {
        this.title = title;
        this.order = order;
    }

}
