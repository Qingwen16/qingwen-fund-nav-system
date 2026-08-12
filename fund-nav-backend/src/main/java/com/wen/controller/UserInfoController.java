package com.wen.controller;

import cn.hutool.core.util.StrUtil;
import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.entity.AccountInfo;
import com.wen.model.entity.UserInfo;
import com.wen.service.UserInfoService;
import com.wen.utils.UserInfoContext;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户信息 Controller
 *
 * @author jwruan
 * @date 2026-08-04
 */
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/queryUserInfo")
    public Response<UserInfo> queryUserInfo() {
        Long userId = UserInfoContext.getUserId();
        UserInfo userInfo = userInfoService.queryUserByUserId(userId);
        return Response.success(userInfo);
    }

    @GetMapping("/deleteUserInfo")
    public Response<?> deleteUserInfo() {
        Long userId = UserInfoContext.getUserId();
        userInfoService.deleteUserInfo(userId);
        return Response.success();
    }

    @GetMapping("/queryUserAccount")
    public Response<List<AccountInfo>> queryUserAccount() {
        Long userId = UserInfoContext.getUserId();
        List<AccountInfo> accountInfoList = userInfoService.queryUserAccount(userId);
        return Response.success(accountInfoList);
    }

    @GetMapping("/updateUserAccount")
    public Response<?> updateUserAccount(@Param("accountId") Long accountId,
                                         @Param("name") String name) {
        if (accountId == null || StrUtil.isEmpty(name)) {
            throw new BusinessException("输入参数存在空值");
        }
        Long userId = UserInfoContext.getUserId();
        userInfoService.updateUserAccount(userId, accountId, name);
        return Response.success();
    }

    @GetMapping("/deleteUserAccount")
    public Response<?> deleteUserAccount(@Param("accountId") Long accountId) {
        if (accountId == null) {
            throw new BusinessException("输入参数存在空值");
        }
        userInfoService.deleteUserAccount(accountId);
        return Response.success();
    }

}
