package com.CStudy.domain.workbook.dto.application;

import com.CStudy.domain.workbook.dto.request.WorkbookQuestionRequestDto;
import com.CStudy.domain.workbook.dto.request.CreateWorkbookRequestDto;
import com.CStudy.domain.workbook.dto.request.UpdateWorkbookRequestDto;
import com.CStudy.domain.workbook.dto.response.WorkbookQuestionResponseDto;
import com.CStudy.domain.workbook.dto.response.WorkbookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WorkbookService {


    Page<WorkbookResponseDto> getWorkbookList(Pageable pageable, String title, String description);

    WorkbookResponseDto getWorkbook(Long id);

    Page<WorkbookQuestionResponseDto> getQuestions(Long id, Pageable pageable);

    void createWorkbook(CreateWorkbookRequestDto workbookDto);

    void addQuestion(WorkbookQuestionRequestDto requestDto);

    void updateWorkbook(UpdateWorkbookRequestDto workbookDto);

    void deleteQuestion(WorkbookQuestionRequestDto requestDto);
}
