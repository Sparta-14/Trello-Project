package com.spata14.trelloproject.card.entity;

import com.spata14.trelloproject.user.User;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table
public class CardUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public CardUser (Card card, User user) {
        this.card = card;
        this.user = user;
    }

    public CardUser() {}
}
