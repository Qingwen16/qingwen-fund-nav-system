package com.wen.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-12
 */
@TableName("t_user_info")
@Data
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 微信开放平台openid
     */
    private String openid;

    /**
     * 微信开放平台unionid
     */
    private String unionid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 创建时间戳
     */
    private Long createTime;

}
