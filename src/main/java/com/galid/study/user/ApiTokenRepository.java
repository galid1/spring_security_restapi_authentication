package com.galid.study.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiTokenRepository extends JpaRepository<ApiTokenEntity, Long> {
    void removeByUserId(Long userId);
    Optional<ApiTokenEntity> findFirstByUserId(Long userId);
}
