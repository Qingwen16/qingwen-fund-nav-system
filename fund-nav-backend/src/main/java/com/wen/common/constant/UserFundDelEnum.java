package com.wen.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Getter
@AllArgsConstructor
public enum UserFundDelEnum {

    /**
     * UserFundDelEnum
     */
    NORMAL(0, "正常"),

    DELETED(1, "删除");

    private final int code;

    private final String desc;

}
