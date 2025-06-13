package com.ecommerce.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.Otp;

public interface OtpRepository extends JpaRepository<Otp, Long>{
    Optional<Otp> findByEmailAndPurpose(String email, String purpose);
}
