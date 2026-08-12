package com.wen.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 11:13
 * 基金通常用 units，股票通常用 shares
 */
@Data
public class PositionRequest {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 单位、份额、单元
     */
    private BigDecimal units;

}
