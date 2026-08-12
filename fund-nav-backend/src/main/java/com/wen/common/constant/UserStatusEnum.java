package com.wen.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    /**
     * UserStatusEnum
     */
    NORMAL(0, "正常"),

    DISABLED(1, "禁用"),

    CANCELLED(2, "注销");

    private final int code;

    private final String desc;

}
