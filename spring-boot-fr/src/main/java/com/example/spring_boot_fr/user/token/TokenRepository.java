package com.example.spring_boot_fr.user.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Dave AKN
 * @version 1.0
 */
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);
}
