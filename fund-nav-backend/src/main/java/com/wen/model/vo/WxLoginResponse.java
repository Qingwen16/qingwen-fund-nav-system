package com.wen.model.vo;

import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
public class WxLoginResponse {

    /**
     * 自定义登录态 token
     */
    private String token;

    /**
     * 系统用户 ID
     */
    private Long userId;

    /**
     * 微信 openid
     */
    private String openid;

}
