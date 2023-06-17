package com.CStudy.domain.workbook.repository;

import com.CStudy.domain.workbook.entity.Workbook;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkbookRepository extends JpaRepository<Workbook, Long>, WorkbookRepositoryCustom {

}
