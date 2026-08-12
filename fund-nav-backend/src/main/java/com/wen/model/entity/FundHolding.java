package com.wen.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 12:03
 * 持有基金（实际持仓）基金通常用 units，股票通常用 shares
 */
@Data
@TableName("t_fund_holding")
public class FundHolding {

    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 持有份额
     */
    private BigDecimal units;

    /**
     * 修改时间
     */
    private Long updateTime;

    /**
     * 创建时间
     */
    private Long createTime;

}
