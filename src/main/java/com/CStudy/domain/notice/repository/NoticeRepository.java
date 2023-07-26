package com.CStudy.domain.notice.repository;

import com.CStudy.domain.notice.entitiy.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    Optional<Notice> findByTitle(String title);
}
