package com.CStudy.domain.refresh.repository;


import com.CStudy.domain.refresh.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepositry extends JpaRepository<RefreshToken, Long> {
}
