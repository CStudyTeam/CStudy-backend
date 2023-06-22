package com.CStudy.domain.workbook.repository;

import com.CStudy.domain.question.entity.Question;
import com.CStudy.domain.workbook.entity.Workbook;
import com.CStudy.domain.workbook.entity.WorkbookQuestion;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkbookQuestionRepository extends JpaRepository<WorkbookQuestion, Long> {

//    @Query(
//        "SELECT wq from WorkbookQuestion wq " +
//        "JOIN Workbook w ON w.id = :id " +
//        "JOIN FETCH Question q " +
//        "ORDER BY w.createdAt desc"
//    )
//    Page<WorkbookQuestion> findWorkbookQuestion(@Param("id") Long id, Pageable pageable);

    Optional<WorkbookQuestion> findByWorkbookAndQuestion(Workbook workbook, Question question);

    boolean existsByWorkbookAndQuestion(Workbook workbook, Question question);
}
