package com.spata14.trelloproject.card.service;

import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.entity.CardEntity;
import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.repository.CardRepository;
import com.spata14.trelloproject.card.repository.FileStorageRepository;
import com.spata14.trelloproject.card.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {
    private final CardRepository cardRepository;
    private final FileStorageRepository fileStorageRepository;

    public CardResponseDto createCard(
            CardRequestDto cardRequestDto,
            List<MultipartFile> multipartFile) throws IOException {

        CardEntity card = new CardEntity(cardRequestDto.getTitle(), cardRequestDto.getContent());
        CardEntity saved = cardRepository.save(card);
        List<FileData> savedFile = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            savedFile.add(fileStorageRepository.save(
                    FileData.builder()
                            .card(saved)
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .fileData(FileUtils.compressfile(file.getBytes()))
                            .build()
            ));
        }
//        FileData savedFile = fileStorageRepository.save(
//                FileData.builder()
//                        .card(saved)
//                        .name(multipartFile.getOriginalFilename())
//                        .type(multipartFile.getContentType())
//                        .fileData(FileUtils.compressfile(multipartFile.getBytes()))
//                        .build()
//        );
        for (FileData s : savedFile) {
            log.info("=== s : ", s.getName());
        }

        log.info("sss saved.getId : {}", saved.getId());
        CardResponseDto cardResponseDto = new CardResponseDto(
                saved.getId(),
                saved.getTitle(),
                saved.getContent(),
                savedFile,
                saved.getCreatedAt(),
                saved.getModifiedAt());
        log.info("--- cardResponseDto :{} " , cardResponseDto.getTitle());
        return cardResponseDto;
    }

    public List<CardResponseDto> readAllCard() {
        List<CardEntity> allCard = cardRepository.findAll();
//        fileStorageRepository.findAllByCardId()
        List<CardResponseDto> responseDtos = new ArrayList<>();
        allCard.forEach(card -> responseDtos.add(
                new CardResponseDto(
                        card.getId(),
                        card.getTitle(),
                        card.getContent(),
                        card.getCreatedAt(),
                        card.getModifiedAt())
        ));
        return responseDtos;
    }
}
