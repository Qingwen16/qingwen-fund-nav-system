package com.wen.controller;

import com.wen.common.exception.BusinessException;
import com.wen.common.response.Response;
import com.wen.model.vo.WxLoginRequest;
import com.wen.model.vo.WxLoginResponse;
import com.wen.service.AuthInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
@RestController
@RequestMapping("/fund/valuation/auth")
@RequiredArgsConstructor
public class AuthInfoController {

    private final AuthInfoService authInfoService;

    @PostMapping("/login")
    public Response<?> login(@RequestBody WxLoginRequest request) {
        if (request == null) {
            throw new BusinessException("输入参数不能为空");
        }
        WxLoginResponse response = authInfoService.login(request.getCode());
        return Response.success(response);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    public Response<?> logout() {
        authInfoService.logout();
        return Response.success("Ok");
    }

}
