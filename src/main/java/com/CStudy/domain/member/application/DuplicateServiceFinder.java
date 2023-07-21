package com.CStudy.domain.member.application;

import com.CStudy.domain.member.dto.response.DuplicateResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DuplicateServiceFinder {

    private final List<DuplicateService>duplicateServices;

    public DuplicateServiceFinder(List<DuplicateService> duplicateServices) {
        this.duplicateServices = duplicateServices;
    }

    public DuplicateResponseDto getVerifyResponseDto(String type, String value) {
        DuplicateService duplicateService = duplicateServices.stream()
                .filter(verifyService -> verifyService.getType().name().equals(type))
                .findAny()
                .orElseThrow(RuntimeException::new);

        return duplicateService.signupDivisionDuplicateCheck(type, value);
    }
}
