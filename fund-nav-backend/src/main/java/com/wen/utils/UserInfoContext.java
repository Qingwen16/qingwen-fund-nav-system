package com.wen.utils;

/**
 * 用户上下文 — ThreadLocal 存储当前请求的会话信息
 *
 * @author jwruan
 * @date 2026-08-04
 */
public class UserInfoContext {

    private static final ThreadLocal<UserSession> SESSION = new ThreadLocal<>();

    /**
     * 设置当前请求的会话（拦截器中调用）
     */
    public static void set(UserSession session) {
        SESSION.set(session);
    }

    /**
     * 获取当前会话
     */
    public static UserSession get() {
        return SESSION.get();
    }

    /**
     * 获取当前登录用户 ID
     */
    public static Long getUserId() {
        UserSession session = SESSION.get();
        return session != null ? session.userId() : null;
    }

    /**
     * 获取当前请求的 token
     */
    public static String getToken() {
        UserSession session = SESSION.get();
        return session != null ? session.token() : null;
    }

    /**
     * 获取当前用户 openid
     */
    public static String getOpenid() {
        UserSession session = SESSION.get();
        return session != null ? session.openid() : null;
    }

    /**
     * 清除上下文（请求结束时必须调用，防止内存泄漏）
     */
    public static void clear() {
        SESSION.remove();
    }

    /**
     * 用户会话信息
     */
    public record UserSession(Long userId, String openid, String token) {
    }

}
