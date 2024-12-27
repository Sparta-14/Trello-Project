package com.spata14.trelloproject.card.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Column(length = 100000)
    @Lob
    private byte[] fileData;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardEntity cardEntity;

    @Builder
    public FileData(CardEntity card, String name, String type, byte[] fileData){
        this.cardEntity = card;
        this.name = name;
        this.type = type;
        this.fileData = fileData;
    }




}
