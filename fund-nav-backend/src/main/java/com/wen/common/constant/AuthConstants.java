package com.wen.common.constant;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public class AuthConstants {

    /**
     * 微信 code2Session 接口地址
     */
    public static final String WX_CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session" +
            "?appid={appid}&secret={secret}&js_code={code}&grant_type=authorization_code";

}
