package com.wen.model.vo;

import lombok.Data;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Data
public class WxLoginRequest {

    /**
     * 微信小程序前端 wx.login() 返回的 code
     */
    private String code;

}
