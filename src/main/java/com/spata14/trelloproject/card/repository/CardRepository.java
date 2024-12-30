package com.spata14.trelloproject.card.repository;

import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.card.exception.CardErrorCode;
import com.spata14.trelloproject.card.exception.CardException;
import com.spata14.trelloproject.search.repository.SearchCardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, SearchCardRepositoryCustom {

    default Card findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(()->
                new CardException(CardErrorCode.CARD_NOT_FOUNT));
    }
}
