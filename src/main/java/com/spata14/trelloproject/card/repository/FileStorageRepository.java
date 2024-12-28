package com.spata14.trelloproject.card.repository;


import com.spata14.trelloproject.card.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileData, Long> {

    FileData findByName(String fileName);

    List<FileData> findAllByCardId(Long id);
}
