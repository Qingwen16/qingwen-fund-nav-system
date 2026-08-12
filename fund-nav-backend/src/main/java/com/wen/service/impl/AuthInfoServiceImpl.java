package com.wen.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.common.constant.AuthConstants;
import com.wen.config.WxConfig;
import com.wen.model.entity.UserInfo;
import com.wen.model.vo.WxLoginResponse;
import com.wen.model.vo.WxSession;
import com.wen.service.AuthInfoService;
import com.wen.service.CacheService;
import com.wen.service.UserInfoService;
import com.wen.utils.JwtUtil;
import com.wen.utils.UserInfoContext;
import com.wen.utils.UserInfoContext.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务实现 — 微信登录 + 无状态 JWT
 *
 * @author jwruan
 * @date 2026-08-04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthInfoServiceImpl implements AuthInfoService {

    private final WxConfig wxConfig;

    private final JwtUtil jwtUtil;

    private final CacheService cacheService;

    private final UserInfoService userInfoService;

    private final RestTemplate restTemplate = new RestTemplate();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public WxLoginResponse login(String code) {
        // 1. 调用微信接口换取 openid
        WxSession wxSession = getWxSession(code);
        if (wxSession == null || wxSession.getOpenid() == null) {
            log.error("微信登录失败，无法获取 openid: {}", wxSession);
            throw new RuntimeException("微信登录失败");
        }

        // 2. 查询或注册用户
        UserInfo userInfo = userInfoService.queryUserInfoByOpenId(wxSession.getOpenid());
        if (userInfo == null) {
            userInfo = userInfoService.registerUserByWxSession(wxSession);
            userInfoService.createUserAccount(userInfo.getUserId(), "默认账户");
        }

        // 3. 生成 JWT（无状态，不存 Redis）
        String token = jwtUtil.generateToken(userInfo.getUserId(), userInfo.getOpenid());

        WxLoginResponse response = new WxLoginResponse();
        response.setToken(token);
        response.setOpenid(userInfo.getOpenid());
        response.setUserId(userInfo.getUserId());

        log.info("user_logged_in userId={} openid={}", userInfo.getUserId(), userInfo.getOpenid());
        return response;
    }

    @Override
    public void logout() {
        UserSession session = UserInfoContext.get();
        if (session == null || session.token() == null) {
            return;
        }

        long remainingMs = jwtUtil.getExpireTime(session.token());
        long ttlSeconds = Math.max(remainingMs / 1000, 1);
        cacheService.setTokenBlacklist(session.token(), ttlSeconds);

        log.info("user_logged_out userId={}", session.userId());
    }

    /**
     * 调用微信 code2Session 接口
     */
    private WxSession getWxSession(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", wxConfig.getAppId());
        params.put("secret", wxConfig.getAppSecret());
        params.put("code", code);

        try {
            String result = restTemplate.getForObject(AuthConstants.WX_CODE2SESSION_URL,
                    String.class, params);
            return objectMapper.readValue(result, WxSession.class);
        } catch (Exception e) {
            throw new RuntimeException("调用微信 code2Session 接口失败: " + e.getMessage());
        }
    }

}
