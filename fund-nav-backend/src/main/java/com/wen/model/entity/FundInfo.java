package com.wen.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@TableName("t_fund_info")
@Data
public class FundInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 更新时间
     */
    private Long createTime;

}
