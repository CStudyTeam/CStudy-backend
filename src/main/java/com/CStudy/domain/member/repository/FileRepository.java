package com.CStudy.domain.member.repository;

import com.CStudy.domain.member.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
