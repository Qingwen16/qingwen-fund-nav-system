package com.wen.service;

import com.wen.model.dto.FundHoldingDto;
import com.wen.model.dto.FundWatchlistDto;
import com.wen.model.vo.HoldingResponse;

import java.util.List;

/**
 * @author : rjw
 * @date : 2026-03-31
 */
public interface RelationService {

    /**
     * 获取用户所有自选基金列表
     *
     * @param userId 用户ID
     * @return 列表
     */
    List<FundWatchlistDto> queryWatchlistList(long userId);

    /**
     * 获取用户所有持有基金列表（多账号）
     *
     * @param userId    用户ID
     * @return 用户[账户[列表]]
     */
    List<HoldingResponse> queryHoldingList(long userId);

    /**
     * 判断自选基金关系是否存在
     *
     * @param userId 用户ID
     * @param code   基金代码
     * @return 是否
     */
    boolean existsWatchlistFund(long userId, String code);

    /**
     * 判断自选基金关系是否存在
     *
     * @param userId    用户ID
     * @param accountId 账户ID
     * @param code      基金代码
     * @return 是否
     */
    boolean existsHoldingFund(long userId, long accountId, String code);

    /**
     * 新增自选基金数据
     *
     * @param request 自选基金类
     */
    void insertWatchlistFund(FundWatchlistDto request);

    /**
     * 新增自选基金数据
     *
     * @param request 自选基金类
     */
    void insertHoldingFund(FundHoldingDto request);

    /**
     * 删除自选基金
     *
     * @param userId 用户ID
     * @param code   基金代码
     */
    void deleteWatchlistFund(long userId, String code);

    /**
     * 取消持有基金数据
     *
     * @param userId    用户ID
     * @param accountId 用户账户ID
     * @param code      基金代码
     */
    void deleteHoldingFund(long userId, long accountId, String code);

}
