package com.spata14.trelloproject.card.dto;

import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.card.entity.CardUser;
import com.spata14.trelloproject.card.entity.FileData;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime endAt;

    private List<String> filesName;
    private List<String> cardUsers;

    private String writeUser;

    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;

    public CardResponseDto(Card card) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.endAt = card.getEndAt();
        this.writeUser = card.getUser().getEmail();
        this.createAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();

    }

    public CardResponseDto(Card card,  List<String> cardUsers) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.endAt = card.getEndAt();

        this.writeUser = card.getUser().getEmail();

        this.createAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();

        this.cardUsers = cardUsers;
    }

    public CardResponseDto(Card card, List<FileData> fileDataList, List<CardUser> cardUserList) {
        this.id = card.getId();
        this.title = card.getTitle();
        this.content = card.getContent();
        this.endAt = card.getEndAt();

        this.writeUser = card.getUser().getEmail();

        this.createAt = card.getCreatedAt();
        this.modifiedAt = card.getModifiedAt();

        this.cardUsers = makeCardUserList(cardUserList);
        this.filesName = makeFilesNameList(fileDataList);

    }

    public List<String> makeFilesNameList(List<FileData> fileDataList) {
        List<String> filesName = new ArrayList<>();
        for (FileData f : fileDataList) {
            filesName.add(f.getName());
        }
        return filesName;
    }

    public List<String> makeCardUserList(List<CardUser> cardUserList) {
        List<String> cardUser = new ArrayList<>();
        for (CardUser cu : cardUserList) {
            cardUser.add(cu.getUser().getName());
        }
        return cardUser;
    }



//    public CardResponseDto(Card card, List<String> filesName) {
//
//    }
//    public CardResponseDto(Long id, String title, String content, List<FileData> fileDataList, LocalDateTime createdAt, LocalDateTime modifiedAt) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.fileDataList = fileDataList;
//        this.createAt = createdAt;
//        this.modifiedAt = modifiedAt;
//    }
//
//    public CardResponseDto(Long id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.createAt = createdAt;
//        this.modifiedAt = modifiedAt;
//    }

}
