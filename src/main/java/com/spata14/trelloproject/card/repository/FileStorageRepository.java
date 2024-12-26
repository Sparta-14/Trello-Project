package com.spata14.trelloproject.card.repository;


import com.spata14.trelloproject.card.entity.FileData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStorageRepository extends JpaRepository<FileData, Long> {

    FileData findByName(String fileName);
}
