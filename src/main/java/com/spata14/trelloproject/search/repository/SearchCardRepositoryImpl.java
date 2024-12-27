package com.spata14.trelloproject.search.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.spata14.trelloproject.card.Card;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import static com.spata14.trelloproject.card.QCard.card;
import static org.springframework.util.StringUtils.hasText;

@RequiredArgsConstructor
public class SearchCardRepositoryImpl implements SearchCardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<Card> findCardsByIdOrTitleOrderByCreatedAtDesc(
            @Nullable  Long cardId,
            @Nullable String title,
            Pageable pageable
    ) {
        QueryResults<Card> results = queryFactory
                .selectFrom(card)
                .where(
                        cardIdEq(cardId),
                        titleLike(title)
                )
                .orderBy(card.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression cardIdEq(Long id) {
        return id != null ? card.id.eq(id) : null;
    }

    private BooleanExpression titleLike(String title) {
        return hasText(title)? card.title.like("%" + title + "%") : null;
    }
}
