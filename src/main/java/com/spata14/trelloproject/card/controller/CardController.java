package com.spata14.trelloproject.card.controller;

import com.spata14.trelloproject.Notification.facade.NotificationServiceFacade;
import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.dto.FileDownloadDto;
import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.service.CardService;
import com.spata14.trelloproject.card.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("workspaces/{workspaceId}/cards")
public class CardController {

    private final CardService cardService;
    private final FileStorageService fileService;
    private final NotificationServiceFacade notificationServiceFacade;

    /*
    카드 생성
     */
    @PostMapping
    public ResponseEntity<?> createCard(
            @PathVariable Long workspaceId,
            @RequestBody CardRequestDto cardRequestDto) {
        CardResponseDto cardResponseDto = cardService.createCard(cardRequestDto);
        notificationServiceFacade.sendSlackMessageForProcessingOutcome(workspaceId, cardResponseDto.getId(), null);
        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDto);
    }

    /*
    카드 전체 조회
     */
    @GetMapping()
    public ResponseEntity<?> readAllCards(){
        List<CardResponseDto> responseDtos = cardService.readAllCard();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    /*
    카드 수정
    Todo : 첨부파일, 담당자리스트까지 구현해야함.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCard(
            @PathVariable("id") Long id,
            @RequestBody CardRequestDto cardRequestDto) {

        return ResponseEntity.status(HttpStatus.OK).body(cardService.updateCard(id, cardRequestDto));
    }


    /*
    파일 업로드
     */
    @PostMapping("/{id}/files")
    public ResponseEntity<?> uploadFiles(
            @PathVariable(value = "id") Long id,
            @RequestPart(value = "files") List<MultipartFile> files) throws IOException {
        List<String> fileList = cardService.uploadFiles(id, files);

        return ResponseEntity.status(HttpStatus.OK).body(fileList + "가 업로드 되었습니다.");

    }

    /*
    파일 다운로드
     */
    @GetMapping("/{id}/files")
    public ResponseEntity<?> downloadFiles(@PathVariable("id") Long id) {
        for (FileDownloadDto downloadFile : cardService.downloadFiles(id)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(downloadFile.getType()))
                    .body(downloadFile.getBytes());
        }
        return ResponseEntity.status(HttpStatus.OK).body("?.?");
    }
}
