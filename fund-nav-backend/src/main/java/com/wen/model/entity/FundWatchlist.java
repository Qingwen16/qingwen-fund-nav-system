package com.wen.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 12:04
 * 自选基金（仅收藏）
 */
@Data
@TableName("t_fund_watchlist")
public class FundWatchlist {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 基金代码
     */
    private String code;

    /**
     * 创建时间
     */
    private Long createTime;

}
