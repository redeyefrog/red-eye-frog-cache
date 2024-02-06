package org.redeyefrog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {

    SUCCESS("0000", "Success"),

    DATA_NOT_EXIST("0003", "Data not exist"),

    REDIS_ERROR("9100", "Execute redis error"),

    SYSTEM_ERROR("9999", "System error");

    private String code;

    private String desc;

}
