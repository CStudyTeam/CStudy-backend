package com.CStudy.domain.workbook.dto.controller;

import com.CStudy.domain.workbook.dto.application.WorkbookService;
import com.CStudy.domain.workbook.dto.request.WorkbookQuestionRequestDto;
import com.CStudy.domain.workbook.dto.request.CreateWorkbookRequestDto;
import com.CStudy.domain.workbook.dto.request.UpdateWorkbookRequestDto;
import com.CStudy.domain.workbook.dto.response.WorkbookQuestionResponseDto;
import com.CStudy.domain.workbook.dto.response.WorkbookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WorkbookController {

    private final WorkbookService workbookService;

    public WorkbookController(WorkbookService workbookService) {
        this.workbookService = workbookService;
    }

    @GetMapping("/workbook/list")
    @ResponseStatus(HttpStatus.OK)
    public Page<WorkbookResponseDto> getWorkbookList(
        @PageableDefault(sort = {"createdAt"}, direction = Direction.DESC) Pageable pageable,
        @RequestParam(value = "title", defaultValue = "") String title,
        @RequestParam(value = "description", defaultValue = "") String description
    ){
        return workbookService.getWorkbookList(pageable, title, description);
    }

    @GetMapping("/workbook/{id}")
    @ResponseStatus(HttpStatus.OK)
    public WorkbookResponseDto getWorkbook(
            @PathVariable("id") Long id
    ){
        return workbookService.getWorkbook(id);
    }

    @GetMapping("/workbook/question/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Page<WorkbookQuestionResponseDto> getQuestions(
            @PageableDefault Pageable pageable,
            @PathVariable("id") Long id
    ) {
        return workbookService.getQuestions(id, pageable);
    }

    @PostMapping("/workbook/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createWorkbook(@RequestBody CreateWorkbookRequestDto workbookDto){
        workbookService.createWorkbook(workbookDto);
    }

    @PostMapping("/workbook/question/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addQuestion(@RequestBody WorkbookQuestionRequestDto requestDto) {
        workbookService.addQuestion(requestDto);
    }

    @PostMapping("/workbook/update")
    @ResponseStatus(HttpStatus.OK)
    public void updateWorkbook(@RequestBody UpdateWorkbookRequestDto workbookDto){
        workbookService.updateWorkbook(workbookDto);
    }

    @DeleteMapping("/workbook/question/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@RequestBody WorkbookQuestionRequestDto requestDto){
        workbookService.deleteQuestion(requestDto);
    }


}
