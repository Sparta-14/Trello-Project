package com.spata14.trelloproject.list.repository;

import com.spata14.trelloproject.list.entity.Lists;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListsRepository extends JpaRepository<Lists, Long> {
}