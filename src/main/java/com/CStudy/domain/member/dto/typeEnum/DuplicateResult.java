package com.CStudy.domain.member.dto.typeEnum;

import lombok.Getter;

@Getter
public enum DuplicateResult {
    TRUE("true"),
    FALSE("false");

    private final String divisionResult;

    DuplicateResult(String divisionResult) {
        this.divisionResult = divisionResult;
    }
}
