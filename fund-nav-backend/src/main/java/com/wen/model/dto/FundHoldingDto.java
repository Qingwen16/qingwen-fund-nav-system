package com.wen.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 13:01
 * 持有基金信息
 */
@Data
public class FundHoldingDto {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户ID
     */
    private Long accountId;

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

    /**
     * 持有份额
     */
    private BigDecimal units;

    /**
     * 最新净值（交易时段为实时估值，非交易时段为昨日单位净值）
     */
    private BigDecimal nav;

    /**
     * 持有市值 = units × nav
     */
    private BigDecimal marketValue;

    /**
     * 估算涨跌幅（%）
     */
    private BigDecimal changePercent;

    /**
     * 净值日期
     */
    private String navDate;

}
