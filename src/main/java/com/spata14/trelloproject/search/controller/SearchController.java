package com.spata14.trelloproject.search.controller;

import com.spata14.trelloproject.search.dto.SearchCardResponseDto;
import com.spata14.trelloproject.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;;

    @GetMapping
    public ResponseEntity<Page<SearchCardResponseDto>> search(
            @RequestParam(required = false) Long cardId,
            @RequestParam(required = false) String title,
            Pageable pageable
    ){
        return ResponseEntity
                .ok()
                .body(searchService.searchCardList(cardId, title, pageable));
    }
}
