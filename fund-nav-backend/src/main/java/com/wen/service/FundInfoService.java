package com.wen.service;

import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.entity.FundInfo;

/**
 * @author : rjw
 * @date : 2026-03-20
 */
public interface FundInfoService {

    /**
     * 查询基金信息
     *
     * @param code 基金代码
     * @return 用户信息
     */
    FundInfo queryFundInfo(String code);

    /**
     * 查询基金信息
     */
    boolean existsFundInfo(String code);

    /**
     * 新增基金信息
     */
    void insertFundInfo(FundWatchlistDto request);

    /**
     * 新增基金信息
     */
    void insertFundInfo(FundHoldingDto request);

    /**
     * 新增基金信息
     */
    void deleteFundInfo(String code);

}
