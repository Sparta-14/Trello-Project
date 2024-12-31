package com.spata14.trelloproject.search.service;

import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.card.repository.CardRepository;
import com.spata14.trelloproject.search.dto.SearchCardResponseDto;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final CardRepository cardRepository;

    public Page<SearchCardResponseDto> searchCardList(
            @Nullable Long cardId,
            @Nullable String title,
            Pageable pageable) {
        Page<Card> cardsByIdOrTitleOrderByCreatedAtDesc = cardRepository.getQueryDSLCardsByIdOrTitleOrderByCreatedAtDesc(cardId, title, pageable);
        return toDto(cardsByIdOrTitleOrderByCreatedAtDesc);
    }

    private Page<SearchCardResponseDto> toDto(Page<Card> cards) {
        return cards.map(Card::toDto);
    }
}
