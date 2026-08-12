package com.wen.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author : 青灯文案
 * @Date: 2026/3/22 11:16
 */
@Data
@TableName("t_account_info")
public class AccountInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 账户名称,账户名称（如：主账户、备用账户）
     */
    private String name;

    /**
     * 账户类型,账户类型（如：主账户、备用账户）
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Long createTime;


}
