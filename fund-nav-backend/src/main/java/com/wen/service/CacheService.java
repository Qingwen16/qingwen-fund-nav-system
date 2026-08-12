package com.wen.service;

/**
 * 缓存服务接口
 *
 * @author jwruan
 * @date 2026-08-04
 */
public interface CacheService {

    /**
     * Token 加入黑名单（退出登录时调用）
     *
     * @param token     JWT token
     * @param ttlSeconds 黑名单有效时长（秒），应为 token 剩余有效期
     */
    void setTokenBlacklist(String token, long ttlSeconds);

    /**
     * 检查 Token 是否在黑名单中
     */
    Boolean hasTokenBlacklist(String token);

    /**
     * 缓存基金净值
     */
    void setFundNav(String code, Object nav);

    /**
     * 获取缓存的基金净值
     */
    <T> T getFundNav(String code, Class<T> clazz);

}
