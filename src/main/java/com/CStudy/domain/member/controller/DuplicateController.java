package com.CStudy.domain.member.controller;

import com.CStudy.domain.member.application.DuplicateServiceFinder;
import com.CStudy.domain.member.dto.response.DuplicateResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DuplicateController {

    private final DuplicateServiceFinder duplicateServiceFinder;

    public DuplicateController(DuplicateServiceFinder duplicateServiceFinder) {
        this.duplicateServiceFinder = duplicateServiceFinder;
    }

    @GetMapping("/email")
    public DuplicateResponseDto checkEmailDuplication(@RequestParam(value = "account", required = true) String account, HttpServletRequest request) {
        return duplicateServiceFinder.getVerifyResponseDto(request.getParameterNames().nextElement(), account);
    }

    @GetMapping("/name")
    public DuplicateResponseDto checkNameDuplication(@RequestParam(value = "account", required = true) String account, HttpServletRequest request) {
        return duplicateServiceFinder.getVerifyResponseDto(request.getParameterNames().nextElement(), account);
    }

}
