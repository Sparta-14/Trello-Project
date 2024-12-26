package com.spata14.trelloproject.card.service;

import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.repository.FileStorageRepository;
import com.spata14.trelloproject.card.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    private final FileStorageRepository fileStorageRepository;

    public String uploadFile(MultipartFile multiFile) throws IOException {

        FileData savedFile = fileStorageRepository.save(
                FileData.builder()
                        .name(multiFile.getOriginalFilename())
                        .type(multiFile.getContentType())
                        .fileData(FileUtils.compressfile(multiFile.getBytes()))
                        .build()
        );
        log.info("ssss : {}", savedFile.getType());

        return "success";
    }

    public FileData downloadFile(String fileName) {
        return fileStorageRepository.findByName(fileName);

    }

//    public Map<String,byte[]> downloadFile(String fileName) {
//        Map<String, byte[]> fileInfo = new HashMap<String, byte[]>();
//        FileData byNameFile = fileStorageRepository.findByName((fileName));
//        String type = byNameFile.getType();
//        byte[] decompressed = FileUtils.decompressFile(byNameFile.getFileData());
//        fileInfo.put(type, decompressed);
//
//        return fileInfo;
//    }
}
