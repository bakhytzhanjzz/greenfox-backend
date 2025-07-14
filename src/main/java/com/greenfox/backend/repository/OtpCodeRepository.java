package com.greenfox.backend.repository;

import com.greenfox.backend.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    Optional<OtpCode> findTopByPhoneNumberAndUsedFalseOrderByCreatedAtDesc(String phoneNumber);
}
