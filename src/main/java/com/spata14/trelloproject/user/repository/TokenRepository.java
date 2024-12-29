package com.spata14.trelloproject.user.repository;

import com.spata14.trelloproject.user.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
