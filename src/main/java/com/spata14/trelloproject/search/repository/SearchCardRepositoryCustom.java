package com.spata14.trelloproject.search.repository;

import com.spata14.trelloproject.card.entity.Card;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchCardRepositoryCustom {
    public Page<Card> getQueryDSLCardsByIdOrTitleOrderByCreatedAtDesc(@Nullable  Long cardId, @Nullable String title, Pageable pageable);
}
