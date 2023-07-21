package com.CStudy.domain.member.application;

import com.CStudy.domain.member.dto.response.DuplicateResponseDto;
import com.CStudy.domain.member.dto.typeEnum.DuplicateType;

public interface DuplicateService {
    DuplicateResponseDto signupDivisionDuplicateCheck(String type, String value);
    DuplicateType getType();
}
