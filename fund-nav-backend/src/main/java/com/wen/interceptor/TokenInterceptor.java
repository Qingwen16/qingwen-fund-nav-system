package com.wen.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wen.service.CacheService;
import com.wen.utils.JwtUtil;
import com.wen.utils.UserInfoContext;
import com.wen.utils.UserInfoContext.UserSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * Token 拦截器 — 标准无状态 JWT 校验
 * 1. 解析 Bearer Token → 校验签名 + 过期时间
 * 2. 检查黑名单（已退出的 token）
 * 3. 将会话信息注入 UserInfoContext（请求结束后自动清理）
 *
 * @author jwruan
 * @date 2026-08-04
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    private final CacheService cacheService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            writeErrorResponse(response, 401, "未登录，请先登录");
            return false;
        }

        String token = authHeader.substring(7);

        // 1. 解析 JWT（自动校验签名 + 过期）
        Long userId;
        String openid;
        try {
            userId = jwtUtil.getUserId(token);
            openid = jwtUtil.getOpenid(token);
        } catch (Exception e) {
            writeErrorResponse(response, 401, "token 无效或已过期");
            return false;
        }

        // 2. 检查黑名单（已退出登录的 token）
        if (cacheService.hasTokenBlacklist(token)) {
            writeErrorResponse(response, 401, "token 已失效，请重新登录");
            return false;
        }

        // 3. 注入会话上下文
        UserInfoContext.set(new UserSession(userId, openid, token));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        UserInfoContext.clear();
    }

    private void writeErrorResponse(HttpServletResponse response, int code, String message)
            throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

}
