package com.wen.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.wen.model.dto.FundNavDTO;
import com.wen.service.CacheService;
import com.wen.service.FundNavService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 基金净值服务实现 — 天天基金实时估值 + Redis 缓存兜底
 *
 * @author jwruan
 * @date 2026-08-04
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class FundNavServiceImpl implements FundNavService {

    private static final String FUND_API_URL = "http://fundgz.1234567.com.cn/js/{}.js";

    private final CacheService cacheService;

    @Override
    public FundNavDTO getFundNav(String code) {
        // 1. 先查 Redis 缓存
        FundNavDTO cached = cacheService.getFundNav(code, FundNavDTO.class);
        if (cached != null) {
            return cached;
        }
        // 2. 缓存未命中，调天天基金接口
        FundNavDTO nav = fetchFromApi(code);
        if (nav != null) {
            cacheService.setFundNav(code, nav);
        }
        return nav;
    }

    private FundNavDTO fetchFromApi(String code) {
        try {
            String url = StrUtil.format(FUND_API_URL, code);
            String response = HttpUtil.get(url);
            if (StrUtil.isEmpty(response)) {
                log.warn("fund_nav_api_empty code={}", code);
                return null;
            }
            // 天天基金返回 JSONP 格式: jsonpgz({...});
            String json = extractJson(response);
            if (json == null) {
                log.warn("fund_nav_parse_failed code={} response={}", code, response);
                return null;
            }
            FundNavDTO nav = JSON.parseObject(json, FundNavDTO.class);
            log.info("fund_nav_fetched code={} gsz={} gszzl={}%", code, nav.getGsz(), nav.getGszzl());
            return nav;
        } catch (Exception e) {
            log.error("fund_nav_api_error code={}", code, e);
            return null;
        }
    }

    /**
     * 从 JSONP 响应中提取 JSON 字符串
     */
    private String extractJson(String response) {
        int start = response.indexOf('(');
        int end = response.lastIndexOf(')');
        if (start == -1 || end == -1 || start >= end) {
            return null;
        }
        return response.substring(start + 1, end);
    }

}
