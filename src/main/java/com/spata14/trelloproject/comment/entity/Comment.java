package com.spata14.trelloproject.comment.entity;

import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String text, Card card, User user) {
        this.text = text;
        this.card = card;
        this.user = user;
    }

    public void updateText(String text) {
        this.text = text;
    }
}
