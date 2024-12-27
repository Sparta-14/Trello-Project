package com.spata14.trelloproject.card.controller;

import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.service.CardService;
import com.spata14.trelloproject.card.service.FileStorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;
    private final FileStorageService fileService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> createCard(
            @RequestPart(value = "cardRequestDto", required = false) CardRequestDto cardRequestDto,
            @RequestPart(value = "file", required = false) List<MultipartFile> multipartFile
            ) throws IOException {

        CardResponseDto card = cardService.createCard(cardRequestDto, multipartFile);

        return new ResponseEntity<>(card,  HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> readAllCards(){
        List<CardResponseDto> responseDtos = cardService.readAllCard();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }


}
