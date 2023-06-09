package com.CStudy.domain.workbook.repository;

import com.CStudy.domain.question.dto.response.QuestionPageWithCategoryAndTitle;
import com.CStudy.domain.workbook.dto.response.WorkbookQuestionResponseDto;
import com.CStudy.domain.workbook.dto.response.WorkbookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkbookRepositoryCustom {

    Page<WorkbookResponseDto> findWorkbookList(Pageable pageable, String title, String description);

    Page<WorkbookQuestionResponseDto> findWorkbookQuestionList(Pageable pageable, Long id);
}
