package com.spata14.trelloproject.card.entity;


import com.spata14.trelloproject.common.BaseEntity;
import com.spata14.trelloproject.search.dto.SearchCardResponseDto;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.workspace.WorkspaceUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Card extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "card",cascade = CascadeType.ALL)
    private List<CardUser> cardUser;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<FileData> files;

    public void updateCard(String title, String content, LocalDateTime endAt) {
        this.title = title;
        this.content = content;
        this.endAt = endAt;

    }

    public SearchCardResponseDto toDto() {
        return SearchCardResponseDto.builder()
                .id(this.id)
                .userId(this.user.getId())
                .title(this.title)
                .build();
    }


}
