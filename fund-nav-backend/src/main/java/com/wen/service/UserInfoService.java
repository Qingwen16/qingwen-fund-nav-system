package com.wen.service;

import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.WxSession;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface UserInfoService {

    /**
     * 根据微信的session注册用户
     */
    UserInfo registerUserByWxSession(WxSession wxSession);

    /**
     * 获取用户信息
     */
    UserInfo queryUserByUserId(long userId);

    /**
     * 获取用户信息
     */
    UserInfo queryUserInfoByOpenId(String openid);

    /**
     * 删除用户信息
     */
    void deleteUserInfo(long userId);

    /**
     * 根据微信的session注册用户
     */
    AccountInfo createUserAccount(long userId, String name);

    /**
     * 获取用户账户信息
     */
    List<AccountInfo> queryUserAccount(long userId);

    /**
     * 修改用户账户信息
     */
    void updateUserAccount(long userId, long accountId, String name);

    /**
     * 删除用户账户信息
     */
    void deleteUserAccount(long accountId);
}
