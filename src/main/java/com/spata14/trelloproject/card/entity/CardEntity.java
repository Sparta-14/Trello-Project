package com.spata14.trelloproject.card.entity;


import com.spata14.trelloproject.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@RequiredArgsConstructor
@Table(name = "card")
public class CardEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
//    private LocalDateTime endAt;

    @OneToMany
    private List<FileData> files;


    public CardEntity(String title, String content) {
        this.title = title;
        this.content = content;
//        this.endAt = endAt;
    }
}
