package com.wen.model.dto;

import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
public class FundInfoDto {

    /**
     * 基金名字
     */
    private String name;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 基金类型
     */
    private String type;

    /**
     * 基金公司
     */
    private String company;

    /**
     * 基金版块
     */
    private String section;

}
