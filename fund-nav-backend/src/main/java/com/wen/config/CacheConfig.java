package com.wen.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存 Key 前缀与 TTL 配置
 *
 * @author jwruan
 * @date 2026-08-04
 */
@Data
@Configuration
public class CacheConfig {

    @Value("${cache.keyNameSpace}")
    private String keyNameSpace;

    @Value("${cache.defaultKeyVersion}")
    private String defaultKeyVersion;

    private TimeUnit defaultTimeUnit = TimeUnit.SECONDS;

    /**
     * 基金净值缓存 TTL（秒），默认 60s
     */
    private long fundNavTimeout = 60;

    /**
     * 项目 key 前缀
     */
    public String prefix() {
        return keyNameSpace + ":" + defaultKeyVersion + ":";
    }

    /**
     * Token 黑名单 Key
     */
    public String getKeyTokenBlacklist(String token) {
        return prefix() + "TokenBlacklist:" + token;
    }

    /**
     * 基金净值缓存 Key
     */
    public String getKeyFundNav(String code) {
        return prefix() + "FundNav:" + code;
    }

}
