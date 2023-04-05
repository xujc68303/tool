package com.xjc.tool.enums;

import java.util.Arrays;
import java.util.Optional;

public enum PasswordTypeEnum {

    NUM(1,"0-9"),

    UPPER_LETTER(2,"A-Z"),

    LOW_LETTER(3, "a-z"),

    SPEC_CHAR(4, "`.~!@#$%^&*()_-");

    private final Integer code;

    private final String type;

    PasswordTypeEnum(Integer code, String type) {
        this.code = code;
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public static PasswordTypeEnum valueOfByCode(Integer code) {
        return Optional.of(Arrays.stream(PasswordTypeEnum.values()).filter(x->x.getCode().equals(code)).findFirst()).get().orElse(null);
    }

}
