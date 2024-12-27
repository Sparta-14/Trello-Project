package com.spata14.trelloproject.card.service;

import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.card.entity.CardUser;
import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.repository.CardRepository;
import com.spata14.trelloproject.card.repository.CardUserRepository;
import com.spata14.trelloproject.card.repository.FileStorageRepository;
import com.spata14.trelloproject.card.utils.FileUtils;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final FileStorageRepository fileStorageRepository;
    private final UserRepository userRepository;
    private final CardUserRepository cardUserRepository;

    public CardResponseDto createCard(CardRequestDto cardRequestDto) {

        Card card = Card.builder()
                .title(cardRequestDto.getTitle())
                .content(cardRequestDto.getContent())
                .endAt(cardRequestDto.getEndAt())
                .build();
        Card savedCard = cardRepository.save(card);

        List<String> inChargeUsers = new ArrayList<>();
        if(!cardRequestDto.getInChargeUsers().isEmpty()) {
            for (Long user : cardRequestDto.getInChargeUsers()) {
                User userById = userRepository.findByIdOrElseThrow(user);
                inChargeUsers.add(userById.getName());

                CardUser cardUser = new CardUser(card, userById);
                cardUserRepository.save(cardUser);
            }
        }

        return  new CardResponseDto(savedCard,inChargeUsers);
    }

    public List<String > uploadFiles(Long id, List<MultipartFile> files) throws IOException{
        Card cardById = cardRepository.findById(id).orElseThrow();
        List<FileData> savedFiles = new ArrayList<>();
        List<String> filesName = new ArrayList<>();

        for (MultipartFile file : files) {
            filesName.add(file.getName());
            savedFiles.add(fileStorageRepository.save(
                    FileData.builder()
                            .card(cardById)
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .fileData(FileUtils.compressfile(file.getBytes()))
                            .build()
            ));
        }
        return filesName;
    }

    public List<CardResponseDto> readAllCard() {
        List<Card> allCard = cardRepository.findAll();
//        fileStorageRepository.findAllByCardId()
        List<CardResponseDto> responseDtos = new ArrayList<>();
        allCard.forEach(card ->{
            Long id = card.getId();
            List<FileData> fileDataList= fileStorageRepository.findAllByCardId(id);
            List<CardUser> cardUserList = cardUserRepository.findAllByCardId(id);
            responseDtos.add(new CardResponseDto(card, fileDataList, cardUserList));
        });
        return responseDtos;
    }


    public CardResponseDto updateCard(Long id, CardRequestDto cardRequestDto) {
        Card card = cardRepository.findById(id).orElseThrow();
        card.updateCard(cardRequestDto.getTitle(), cardRequestDto.getContent(), cardRequestDto.getEndAt());
        Card savedCard = cardRepository.save(card);
        return new CardResponseDto(savedCard);
    }
}
