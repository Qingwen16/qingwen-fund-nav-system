package com.wen.service.impl;

import com.wen.config.CacheConfig;
import com.wen.service.CacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 缓存服务实现
 *
 * @author jwruan
 * @date 2026-08-04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheServiceImpl implements CacheService {

    private final CacheConfig cacheConfig;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setTokenBlacklist(String token, long ttlSeconds) {
        String tokenKey = cacheConfig.getKeyTokenBlacklist(token);
        redisTemplate.opsForValue().set(tokenKey, "1", ttlSeconds,
                cacheConfig.getDefaultTimeUnit());
    }

    @Override
    public Boolean hasTokenBlacklist(String token) {
        String tokenKey = cacheConfig.getKeyTokenBlacklist(token);
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKey));
    }

    @Override
    public void setFundNav(String code, Object nav) {
        String key = cacheConfig.getKeyFundNav(code);
        redisTemplate.opsForValue().set(key, nav, cacheConfig.getFundNavTimeout(),
                cacheConfig.getDefaultTimeUnit());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getFundNav(String code, Class<T> clazz) {
        String key = cacheConfig.getKeyFundNav(code);
        Object value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }
        return (T) value;
    }

}
