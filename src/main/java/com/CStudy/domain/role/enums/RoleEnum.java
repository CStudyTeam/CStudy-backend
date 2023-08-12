package com.CStudy.domain.role.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {

    CUSTOM("ROLE_CUSTOM"),
    ADMIN("ROLE_ADMIN");

    private final String roleName;


    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

}
