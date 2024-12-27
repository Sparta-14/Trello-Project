package com.spata14.trelloproject.card.controller;

import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.service.CardService;
import com.spata14.trelloproject.card.service.FileStorageService;
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

    /*
    카드 생성
     */
    @PostMapping
    public ResponseEntity<?> createCard(@RequestBody CardRequestDto cardRequestDto) {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.createCard(cardRequestDto));
    }

    /*
    카드 전체 조회
     */
    @GetMapping()
    public ResponseEntity<?> readAllCards(){
        List<CardResponseDto> responseDtos = cardService.readAllCard();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

//    @PatchMapping("/{id}")
//    public ResponseEntity<?> updateCard(@PathVariable("id") Long id) {
//        cardService.updateCard(id);
//    }


    /*
    파일 업로드
     */
    @PostMapping("/{id}/files")
    public ResponseEntity<?> uploadFiles(
            @PathVariable(value = "id") Long id,
            @RequestPart(value = "files") List<MultipartFile> files) throws IOException {
        List<String> fileList = cardService.uploadFiles(id, files);
        log.info(fileList.get(0));

        return ResponseEntity.status(HttpStatus.OK).body(fileList + "가 업로드 되었습니다.");

    }




}
