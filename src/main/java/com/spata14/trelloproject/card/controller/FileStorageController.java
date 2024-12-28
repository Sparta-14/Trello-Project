package com.spata14.trelloproject.card.controller;

import com.spata14.trelloproject.card.entity.FileData;
import com.spata14.trelloproject.card.service.FileStorageService;
import com.spata14.trelloproject.card.utils.FileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileStorageController {

    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile multiFile) throws IOException {
        log.info("ccc : {}", multiFile.getSize());
        fileStorageService.uploadFile(multiFile);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileName") String fileName) {

        FileData downloaded = fileStorageService.downloadFile(fileName);
        byte[] decompressed = FileUtils.decompressFile(downloaded.getFileData());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf(downloaded.getType()))
                .body(decompressed);
    }


}
