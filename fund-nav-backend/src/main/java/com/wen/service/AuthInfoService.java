package com.wen.service;

import com.wen.model.vo.WxLoginResponse;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface AuthInfoService {

    /**
     * 微信用户登录
     */
    WxLoginResponse login(String code);

    /**
     * 微信用户登出
     */
    void logout();
}
