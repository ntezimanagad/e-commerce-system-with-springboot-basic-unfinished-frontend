package com.ecommerce.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.model.BlackList;

public interface TokenBlacklistRepository extends JpaRepository<BlackList, String>{
    boolean existsByToken(String token);
}
