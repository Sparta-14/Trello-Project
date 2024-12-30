package com.spata14.trelloproject.card.controller;

import com.spata14.trelloproject.Notification.facade.NotificationServiceFacade;
import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.dto.FileDownloadDto;
import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.exception.CardErrorCode;
import com.spata14.trelloproject.card.exception.CardException;
import com.spata14.trelloproject.card.service.CardService;
import com.spata14.trelloproject.card.service.FileStorageService;
import com.spata14.trelloproject.common.SessionNames;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/workspaces/{workspaceId}/cards")
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
            @RequestBody CardRequestDto cardRequestDto,
            HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession();
        CardResponseDto cardResponseDto = cardService.createCard(cardRequestDto, session);
        notificationServiceFacade.sendSlackMessageForProcessingOutcome(workspaceId, cardResponseDto.getId(), null);

        return ResponseEntity.status(HttpStatus.OK).body(cardResponseDto);
    }

    /*
    카드 전체 조회
     */
    @GetMapping()
    public ResponseEntity<?> readAllCards() {
        List<CardResponseDto> responseDtos = cardService.readAllCard();
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    /*
    카드 수정
     */
    @PutMapping("/{cardId}")
    public ResponseEntity<?> updateCard(
            @PathVariable("cardId") Long cardId,
            @RequestBody CardRequestDto cardRequestDto,
            HttpServletRequest httpServletRequest) {

        String userAuth = httpServletRequest.getSession().getAttribute("USER_AUTH").toString();

        return ResponseEntity.status(HttpStatus.OK).body(cardService.updateCard(cardId, cardRequestDto, userAuth));
    }

    /*
    카드 삭제
     */
    @Transactional
    @DeleteMapping("/{cardId}")
    public ResponseEntity<String> deleteCard(
            @PathVariable("cardId") Long cardId,
            HttpServletRequest httpServletRequest) {
        String userAuth = httpServletRequest.getSession().getAttribute("USER_AUTH").toString();
        return ResponseEntity.status(HttpStatus.OK).body(cardService.deleteCard(cardId, userAuth));
    }


    /*
    파일 업로드
     */
    @PostMapping("/{cardId}/files")
    public ResponseEntity<?> uploadFiles(
            @PathVariable(value = "cardId") Long id,
            @RequestPart(value = "files") List<MultipartFile> files) throws IOException {
        List<String> fileList = cardService.uploadFiles(id, files);

        return ResponseEntity.status(HttpStatus.OK).body(fileList + "가 업로드 되었습니다.");

    }

    /*
    파일 다운로드 : 이미지 형태만 포스트맨에서 보여지고 나머지 파일들은 다운로드 형태로 처리.
     */
    @GetMapping("/{cardId}/files")
    public ResponseEntity<?> downloadFiles(
            @PathVariable("cardId") Long id,
            @PathVariable("workspaceId") Long workspaceId
        ) {
        List<FileDownloadDto> fileDownloadDtos = cardService.downloadFiles(id);

        if(fileDownloadDtos.size() == 1) {
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(fileDownloadDtos.get(0).getType()))
                    .body(fileDownloadDtos.get(0).getBytes());
        }

        Resource resource = null;
        Map<String, String > fileMap = new HashMap<>();
        log.info("CC  : size : {} ", cardService.downloadFiles(id).size());
        for (FileDownloadDto downloadFile : fileDownloadDtos) {
////            Path path = Paths.get(downloadFile.getName());
//            String path = "localhost:8080/workspaces/"+workspaceId+"/card/"+id + downloadFile.getName();
//            try {
////                resource = new UrlResource(path.toUri());
//                resource = new UrlResource(path);
//
//            } catch (MalformedURLException e) {
//                throw new CardException(CardErrorCode.FILE_NOT_FOUNT);
//            }

            fileMap.put(downloadFile.getName(), "localhost:8080/workspaces/"+workspaceId+"/card/"+id + downloadFile.getName());

//            return ResponseEntity.status(HttpStatus.OK)
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; fileName\"" + downloadFile.getName()+"\";")
//                    .header(HttpHeaders.CONTENT_ENCODING,"binary")
//                    .body(downloadFile.getBytes());
//            return ResponseEntity.status(HttpStatus.OK)
//                    .contentType(MediaType.valueOf(downloadFile.getType()))
//                    .body(downloadFile.getBytes());
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileMap);

    }


    @GetMapping("/{cardId}/files/{fileId}")
    public ResponseEntity<?> downloadFileName(@PathVariable("fileId") Long fileId ) {
        FileDownloadDto fileData = cardService.downloadFileName(fileId);
        log.info("--- {}" ,fileData.getType());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(fileData.getType()))
                .body(fileData.getBytes());

    }
}
