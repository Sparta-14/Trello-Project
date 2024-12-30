package com.spata14.trelloproject.card.service;

import com.spata14.trelloproject.card.dto.CardRequestDto;
import com.spata14.trelloproject.card.dto.CardResponseDto;
import com.spata14.trelloproject.card.dto.FileDownloadDto;
import com.spata14.trelloproject.card.entity.Card;
import com.spata14.trelloproject.card.entity.CardUser;
import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.exception.CardErrorCode;
import com.spata14.trelloproject.card.exception.CardException;
import com.spata14.trelloproject.card.repository.CardRepository;
import com.spata14.trelloproject.card.repository.CardUserRepository;
import com.spata14.trelloproject.card.repository.FileStorageRepository;
import com.spata14.trelloproject.card.utils.FileUtils;
import com.spata14.trelloproject.user.User;
import com.spata14.trelloproject.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    /*
    카드생성 : 지정 담당자는 없을 수도 있음.
     */
    public CardResponseDto createCard(CardRequestDto cardRequestDto, HttpSession session) {

        User userAuth = userRepository.findByEmailOrElseThrow(session.getAttribute("USER_AUTH").toString());
        Card card = Card.builder()
                .title(cardRequestDto.getTitle())
                .content(cardRequestDto.getContent())
                .endAt(cardRequestDto.getEndAt())
                .user(userAuth)
                .build();
        Card savedCard = cardRepository.save(card);

        List<String> inChargeUsers = new ArrayList<>();
        if (!cardRequestDto.getInChargeUsers().isEmpty()) {
            for (Long userId : cardRequestDto.getInChargeUsers()) {
                User userById = userRepository.findByIdOrElseThrow(userId);
                inChargeUsers.add(userById.getName());

                CardUser cardUser = new CardUser(card, userById);
                cardUserRepository.save(cardUser);
            }
        }

        return new CardResponseDto(savedCard, inChargeUsers);
    }

    /*
    카드 전체 조회 : 첨부파일과 담당자는 배열에 이름값만 넣어서 response
     */
    public List<CardResponseDto> readAllCard() {
        List<Card> allCard = cardRepository.findAll();
        List<CardResponseDto> responseDtos = new ArrayList<>();
        allCard.forEach(card -> {
            Long id = card.getId();
            List<FileData> fileDataList = fileStorageRepository.findAllByCardId(id);
            List<CardUser> cardUserList = cardUserRepository.findAllByCardId(id);
            responseDtos.add(new CardResponseDto(card, fileDataList, cardUserList));
        });
        return responseDtos;
    }

    /*
    카드 수정 : 작성자만 수정이 가능함. title, content 변경만 가능.  첨부파일 변경은 따로 처리함.
     */
    public CardResponseDto updateCard(Long cardId, CardRequestDto cardRequestDto, String userAuth) {

        Card savedCard = cardRepository.findByIdOrElseThrow(cardId);
        if (!savedCard.getUser().getEmail().equals(userAuth)) {
            throw new CardException(CardErrorCode.ONLY_WRITER_UPDATE);
        }
        savedCard.updateCard(
                cardRequestDto.getTitle(),
                cardRequestDto.getContent(),
                cardRequestDto.getEndAt());

        List<String> inChargeUsers = new ArrayList<>();
        if (!cardRequestDto.getInChargeUsers().isEmpty()) {
            savedCard.getCardUser().stream().forEach(c -> {
                cardUserRepository.deleteById(c.getId());
            });

            for (Long userId : cardRequestDto.getInChargeUsers()) {
                User userById = userRepository.findByIdOrElseThrow(userId);
                inChargeUsers.add(userById.getName());
                CardUser cardUser = new CardUser(savedCard, userById);
                cardUserRepository.save(cardUser);
            }
        }

        return new CardResponseDto(savedCard, inChargeUsers);
    }


    /*
    첨부파일 업로드 : 카드를 생성할 때 한 번에 업로드 할 수 있지만 api를 분리함.
     */
    public List<String> uploadFiles(Long id, List<MultipartFile> files) throws IOException {
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

    /*
    첨부파일 다운로드
     */
    public List<FileDownloadDto> downloadFiles(Long id) {
        List<FileDownloadDto> fileDownloadDtos = new ArrayList<>();
        for (FileData file : fileStorageRepository.findAllByCardId(id)) {
            Long fileId = file.getId();
            String name = file.getName();
            byte[] decompress = FileUtils.decompressFile(file.getFileData());
            String type = file.getType();
            fileDownloadDtos.add(new FileDownloadDto(fileId, name, decompress, type));
        }
        return fileDownloadDtos;
    }

    public FileDownloadDto downloadFileName(Long fileId) {
        FileData fileData = fileStorageRepository.findById(fileId).orElseThrow();
        return new FileDownloadDto(
                fileData.getId(),
                fileData.getName(),
                FileUtils.decompressFile(fileData.getFileData()),
                fileData.getType());
    }

    public String deleteCard(Long cardId, String userAuth) {
        Card savedCard = cardRepository.findByIdOrElseThrow(cardId);
        if (userAuth.equals(savedCard.getUser().getEmail())) {
            cardRepository.deleteById(cardId);
            return "삭제가 완료되었습니다.";
        }

        return "삭제가 완료되지 않았습니다.";
    }
}
