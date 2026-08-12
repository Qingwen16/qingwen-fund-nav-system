package com.wen.model.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-20
 * 微信 code2Session 接口返回结果
 */
@Data
public class WxSession {

    /**
     * 微信开放平台openid
     */
    private String openid;

    /**
     * sessionKey
     */
    @JsonProperty("session_key")
    private String sessionKey;

    /**
     * 微信开放平台unionid
     */
    private String unionid;

    /**
     * 错误代码
     */
    private Integer errCode;

    /**
     * 错误信息
     */
    private String errMsg;

}