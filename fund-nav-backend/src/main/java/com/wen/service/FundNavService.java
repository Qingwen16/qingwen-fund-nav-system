package com.wen.service;

import com.wen.model.dto.FundNavDTO;

/**
 * 基金净值服务 — 实时拉取天天基金估值 + Redis 缓存
 *
 * @author jwruan
 * @date 2026-08-04
 */
public interface FundNavService {

    /**
     * 根据基金代码获取净值（优先缓存，未命中则调天天基金接口）
     */
    FundNavDTO getFundNav(String code);

}
