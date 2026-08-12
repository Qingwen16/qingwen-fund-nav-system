package com.wen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wen.common.exception.BusinessException;
import com.wen.mapper.AccountInfoMapper;
import com.wen.mapper.UserInfoMapper;
import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.WxSession;
import com.wen.service.UserInfoService;
import com.wen.utils.UserIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    private final AccountInfoMapper accountInfoMapper;

    @Override
    public UserInfo registerUserByWxSession(WxSession wxSession) {
        log.info("注册用户信息: [{}]", wxSession);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(UserIdGenerator.generator());
        userInfo.setNickname("正式用户");
        userInfo.setOpenid(wxSession.getOpenid());
        userInfo.setUnionid(wxSession.getUnionid());
        userInfo.setCreateTime(System.currentTimeMillis());
        userInfoMapper.insert(userInfo);
        log.info("RegisterUserByWxSession：新增用户信息成功: {}", userInfo);
        return userInfo;
    }

    @Override
    public UserInfo queryUserByUserId(long userId) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUserId, userId);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        log.info("QueryUserByUserId：根据用户ID获取用户信息成功: {}", userId);
        return userInfo;
    }

    @Override
    public UserInfo queryUserInfoByOpenId(String openid) {
        if (StrUtil.isEmpty(openid)) {
            log.error("输入参数存在空值: [{}]", openid);
            throw new BusinessException("输入参数存在空值");
        }
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getOpenid, openid);
        UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
        log.info("QueryUserInfoByOpenId：根据openID获取用户信息成功: {}", openid);
        return userInfo;
    }

    @Override
    public void deleteUserInfo(long userId) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUserId, userId);
        userInfoMapper.delete(queryWrapper);
        log.info("DeleteUserInfo：根据用户ID删除用户信息成功: {}", userId);
    }

    @Override
    public AccountInfo createUserAccount(long userId, String name) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setUserId(userId);
        accountInfo.setName(name);
        accountInfo.setType(0);
        accountInfo.setCreateTime(System.currentTimeMillis());
        accountInfoMapper.insert(accountInfo);
        log.info("CreateUserAccount：新增用户账号信息成功: {}", accountInfo);
        return accountInfo;
    }

    @Override
    public List<AccountInfo> queryUserAccount(long userId) {
        LambdaQueryWrapper<AccountInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountInfo::getUserId, userId);
        List<AccountInfo> accountInfoList = accountInfoMapper.selectList(wrapper);
        log.info("QueryUserAccount：根据用户ID获取用户账户信息成功: {}", accountInfoList.size());
        return accountInfoList;
    }

    @Override
    public void updateUserAccount(long userId, long accountId, String name) {
        LambdaUpdateWrapper<AccountInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AccountInfo::getId, accountId);
        wrapper.eq(AccountInfo::getUserId, userId);
        wrapper.set(AccountInfo::getName, name);
        accountInfoMapper.update(wrapper);
        log.info("UpdateUserAccount：修改用户账户信息成功, userId: {}, accountId: {}, name: {}",
                userId, accountId, name);
    }

    @Override
    public void deleteUserAccount(long accountId) {
        accountInfoMapper.deleteById(accountId);
        log.info("DeleteUserAccount：根据用户ID获取用户账户信息成功: {}", accountId);
    }
}

